package pl.maciejkizlich.interview.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookPopularity;
import pl.maciejkizlich.interview.persistence.model.BookRating;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.*;

/**
 * Created by Levon_Movsesyan on 8/20/2014.
 */
@Repository
public class BookRatingRepositoryImpl extends AbstractModelRepository<BookRating> implements BookRatingRepository {

    @Autowired
    private BookRepository bookRepository;

    protected BookRatingRepositoryImpl() {
        super(BookRating.class);
    }

    @Override
    public BookRating getRating(long userId, long bookId) {
        Query q = em.createQuery("SELECT o FROM BookRating o WHERE o.user.id=:userId and o.book.id=:bookId")
                .setParameter("userId", userId)
                .setParameter("bookId", bookId);
        BookRating bookRating;
        try {
            bookRating = (BookRating) q.getSingleResult();
        } catch (NoResultException e) {
            bookRating = null;
        }
        return bookRating;
    }

    @Override
    public Collection<BookPopularity> findMostRatedBooks(int numberOfBooks) {
        Query q = em.createQuery("SELECT b.book.id, avg(b.rating) FROM BookRating b WHERE (SELECT distinct o.book" +
                " FROM BookRating o WHERE b.book.id = o.book.id) > 0 GROUP BY b.book.id");
        List result = q.getResultList();
        Map<Long, Double> bookToRatingAvg = new HashMap<>();
        for (Object o : result) {
            Object[] params = (Object[])o;
            bookToRatingAvg.put((Long) params[0], (Double) params[1]);
        }

        if (!bookToRatingAvg.isEmpty()) {
            Set<Long> bookIds = bookToRatingAvg.keySet();
            Collection<Book> books = bookRepository.findByIds(bookIds);

            List<BookPopularity> bookPopularityList = new ArrayList<>();
            for (Book b : books) {
                BookPopularity bookPopularity = new BookPopularity(b);
                bookPopularity.setValue(bookToRatingAvg.get(b.getId()));
                bookPopularityList.add(bookPopularity);
            }

            Collections.sort(bookPopularityList, new BookPopularity.FamousComparator());

            final int length = bookPopularityList.size();
            numberOfBooks = length < numberOfBooks ? length : numberOfBooks;
            return bookPopularityList.subList(0, numberOfBooks);
        }

        return Collections.emptyList();
    }
}
