package com.epam.library.service;

import static com.epam.library.TestData.PREDEFINED_ADMIN_USER;
import static com.epam.library.TestData.PREDEFINED_POLISH_BOOK;
import static com.epam.library.TestData.PREDEFINED_SIMPLE_USER;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.maciejkizlich.interview.persistence.model.BookFeedback;

import com.epam.library.AbstractTest;

/**
 * Created by Levon_Movsesyan on 8/14/2014.
 */
public class BookServiceFeedbackTest extends AbstractTest {

    final static String FEEDBACK_MESSAGE = "You should read this book";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldLeaveFeedback() {

        // put feedback
        bookService.feedback(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_POLISH_BOOK.getId(), FEEDBACK_MESSAGE);

        List<BookFeedback> feedback = bookService.findBookFeedback(PREDEFINED_POLISH_BOOK.getId());

        assertNotNullAndNotZeroElements(feedback);
        Assert.assertEquals(feedback.size(), 1);

        final Long bookId = feedback.get(0).getBook().getId();
        Assert.assertEquals(PREDEFINED_POLISH_BOOK.getId(), bookId);

        final Long userId = feedback.get(0).getUser().getId();
        Assert.assertEquals(PREDEFINED_SIMPLE_USER.getId(), userId);

        String message = feedback.get(0).getMessage();
        Assert.assertEquals(FEEDBACK_MESSAGE, message);
    }

    @Test
    public void shouldLeaveMultipleFeedback() {

        final int feedbackCount = 20;

        // put multiple feedback
        for (int i = 0; i < feedbackCount; ++i) {
            bookService.feedback(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_POLISH_BOOK.getId(), FEEDBACK_MESSAGE + i);
        }

        List<BookFeedback> feedback = bookService.findBookFeedback(PREDEFINED_POLISH_BOOK.getId());

        assertNotNullAndNotZeroElements(feedback);
        Assert.assertEquals(feedback.size(), feedbackCount);
    }

    @Test
    public void shouldGetUserFeedback() {
        // users put feedback
        final int simpleUserFeedbackCount = 20;
        for (int i = 0; i < simpleUserFeedbackCount; ++i) {
            bookService.feedback(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_POLISH_BOOK.getId(), FEEDBACK_MESSAGE + i);
        }

        final int adminUserFeedbackCount = 14;
        for (int i = 0; i < adminUserFeedbackCount; ++i) {
            bookService.feedback(PREDEFINED_ADMIN_USER.getId(), PREDEFINED_POLISH_BOOK.getId(), FEEDBACK_MESSAGE + i);
        }

        List<BookFeedback> feedback;

        // retrieve feedback for simple user
        feedback = userService.findUserFeedback(PREDEFINED_SIMPLE_USER.getId());
        assertNotNullAndNotZeroElements(feedback);
        Assert.assertEquals(feedback.size(), simpleUserFeedbackCount);

        // retrieve feedback for admin user
        feedback = userService.findUserFeedback(PREDEFINED_ADMIN_USER.getId());
        assertNotNullAndNotZeroElements(feedback);
        Assert.assertEquals(feedback.size(), adminUserFeedbackCount);
    }
}
