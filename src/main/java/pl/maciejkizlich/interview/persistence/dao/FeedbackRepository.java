package pl.maciejkizlich.interview.persistence.dao;

import java.util.List;

import pl.maciejkizlich.interview.persistence.model.BookFeedback;

/**
 * Created by Levon_Movsesyan on 8/13/2014.
 */
public interface FeedbackRepository {

    /**
     * Save feedback into DB
     */
    BookFeedback save(BookFeedback feedback);

    /**
     * Retrieves book's all feedback
     */
    List<BookFeedback> getByBookId(long bookId);

    /**
     * Retrieves user's all feedback
     */
    List<BookFeedback> getByUserId(Long userId);
}
