package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.BookPopularity;
import pl.maciejkizlich.interview.persistence.model.BookRating;

/**
 * Created by Levon_Movsesyan on 8/20/2014.
 */
public interface BookRatingRepository {

    /**
     * Save book rating into DB
     */
    BookRating save(BookRating bookRating);

    /**
     * Remove book rating from DB
     */
    void remove(BookRating bookRating);
    /**
     * Retrieves user's feedback for given book
     */
    BookRating getRating(long userId, long bookId);
    /**
     *  Retrieves top read books
     */
    Collection<BookPopularity> findMostRatedBooks(int numberOfBooks);
}
