package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;
import java.util.Set;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookPopularity;
import pl.maciejkizlich.interview.persistence.model.BookSearch;
import pl.maciejkizlich.interview.persistence.model.Books;

public interface BookRepository {

    /**
     * Save an <code>Book</code> to the data store, either inserting or updating
     * it.
     *
     * @param book the <code>Book</code> to save
     */
    void save(Book book);

    Book findById(Long id);

    Collection<Book> findByIds(Set<Long> ids);

    Book findByName(String name);
    
    Book findByAuthor(String author);
    
    Collection<Book> findAll();

    Collection<BookPopularity> findMostReadBooks(int numberOfBooks);

    Books searchBooks(BookSearch bookSearch);
}
