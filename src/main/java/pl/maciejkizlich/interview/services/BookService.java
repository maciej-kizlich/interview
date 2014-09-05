package pl.maciejkizlich.interview.services;

import java.util.Collection;
import java.util.List;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookFavorite;
import pl.maciejkizlich.interview.persistence.model.BookFeedback;
import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookPopularity;
import pl.maciejkizlich.interview.persistence.model.BookSearch;
import pl.maciejkizlich.interview.persistence.model.Books;

/**
 *
 * @author Oleksandr_Kalnyi
 */
public interface BookService {

    void removeBook(Long id);

    void saveOrUpdateBook(Book book);

    BookOrder reserveBook(long bookId, long userId);

    Book findBook(Long id);
    
    BookOrder borrowBook(long userId, long orderId);
    
    BookOrder returnBook(long bookId, long userId, long orderId);

    BookFeedback feedback(long userId, long bookId, String message);

    List<BookFeedback> findBookFeedback(long bookId);
    /**
     * Put rating for the book
     */
    void evaluateBook(long userId, long bookId, int rateValue);
    /**
     * Retrieves user's rating for the book
     */
    int findBookRating(long userId, long bookId);

    /**
     * Retrieves most read books
     * @param numberOfBooks number of books to retrieve
     */
    Collection<BookPopularity> findMostReadBooks(int numberOfBooks);

    /**
     * Retrieves most rated books
     * @param numberOfBooks number of books to retrieve
     */
    Collection<BookPopularity> findMostRatedBooks(int numberOfBooks);

    /**
     * <pre>Search book with given criterion</pre>
     * <pre>NOTE: To get all books just pass new created instance of {@link BookSearch} class as parameter</pre>
     */
    Books searchBook(BookSearch bookSearch);

    /**
     * add book to favorites
     */
    void addToFavorites(long userId, long bookId);

    /**
     * remove book from favorites
     */
    void removeFromFavorites(long userId, long bookId);

    /**
     * get user's favorite books
     */
    Collection<BookFavorite> getFavorites(long userId);

    /**
     * is the book user's favorite one?
     */
    boolean isFavoriteBook(long userId, long bookId);
}
