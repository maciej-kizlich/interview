package pl.maciejkizlich.interview.persistence.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookPopularity;
import pl.maciejkizlich.interview.persistence.model.BookRating;
import pl.maciejkizlich.interview.persistence.model.BookSearch;
import pl.maciejkizlich.interview.persistence.model.Books;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Collection<Book> findByIds(Set<Long> ids) {
        Query query = em.createQuery("SELECT b FROM Book b where b.id in (:ids)");
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public Book findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Book findByAuthor(String author) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Book> findAll() {
        Query q = em.createQuery("SELECT b FROM Book b");
        return q.getResultList();
    }


    @Override
    public Collection<BookPopularity> findMostReadBooks(int numberOfBooks) {
        Query q = em.createQuery("SELECT b.book.id, count(b.book) FROM BookOrder b WHERE (SELECT distinct o.book" +
                " FROM BookOrder o WHERE b.book.id = o.book.id) > 0 GROUP BY b.book.id");
        List result = q.getResultList();
        Map<Long, Long> bookToReadCount = new HashMap<>();
        for (Object o : result) {
            Object[] params = (Object[])o;
            bookToReadCount.put((Long)params[0], (Long)params[1]);
        }

        if (!bookToReadCount.isEmpty()) {
            Set<Long> bookIds = bookToReadCount.keySet();
            Collection<Book> books = findByIds(bookIds);

            List<BookPopularity> bookPopularityList = new ArrayList<>();
            for (Book b : books) {
                BookPopularity bookPopularity = new BookPopularity(b);
                bookPopularity.setValue(bookToReadCount.get(b.getId()));
                bookPopularityList.add(bookPopularity);
            }

            Collections.sort(bookPopularityList, new BookPopularity.FamousComparator());

            final int length = bookPopularityList.size();
            numberOfBooks = length < numberOfBooks ? length : numberOfBooks;
            return bookPopularityList.subList(0, numberOfBooks);
        }
        return Collections.emptyList();
    }

    @Override
    public Books searchBooks(BookSearch bookSearch) {
        String query = "SELECT r, b FROM BookRating r right outer join r.book b WHERE ";

        if (bookSearch.getId() < 1) {
            query += "(lower(b.author) LIKE lower(:author)) AND (lower(b.title) LIKE lower(:title))";
            if (bookSearch.getRating() > 0) {
                query += " AND (SELECT r.rating FROM BookRating r WHERE (r.book.id = b.id)) >= :rating ";
            }
        } else {
            query += "(b.id = :bookId)";
        }

        Query q = em.createQuery(query);
        if (bookSearch.getId() < 1) {
            q.setParameter("author", "%" + bookSearch.getAuthor().toLowerCase() + "%");
            q.setParameter("title", "%" + bookSearch.getTitle().toLowerCase() + "%");
            if (bookSearch.getRating() > 0) {
                q.setParameter("rating", bookSearch.getRating());
            }
        } else {
            q.setParameter("bookId", bookSearch.getId());
        }

        List<Object[]> resultList = q.getResultList();

        Books books = new Books();
        for (Object[] arr : resultList) {
            assert arr.length == 2;
            Books.BookData bookData = new Books.BookData();
            if (arr[0] != null) {
                BookRating bookRating = (BookRating)arr[0];
                bookData.setRating(bookRating.getRating());
            }
            if (arr[1] != null) {
                Book book = (Book)arr[1];
                bookData.setBook(book);
            }
            books.addBook(bookData);
        }
        return books;
    }
}
