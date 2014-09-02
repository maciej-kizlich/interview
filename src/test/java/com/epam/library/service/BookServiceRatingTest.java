package com.epam.library.service;

import static com.epam.library.TestData.PREDEFINED_ADMIN_USER;
import static com.epam.library.TestData.PREDEFINED_IRISH_BOOK;
import static com.epam.library.TestData.PREDEFINED_POLISH_BOOK;
import static com.epam.library.TestData.PREDEFINED_SIMPLE_USER;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookPopularity;

import com.epam.library.AbstractTest;
import com.epam.library.TestData;

/**
 * Created by Levon_Movsesyan on 8/18/2014.
 */
public class BookServiceRatingTest extends AbstractTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGetSinglePopularBook() {
        BookOrder reservedOrder = bookService.reserveBook(PREDEFINED_POLISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
        bookOrderRepository.save(reservedOrder);
        Collection<BookPopularity> books = bookRepository.findMostReadBooks(5);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.iterator().next().getBook().getId(), PREDEFINED_POLISH_BOOK.getId());
    }

    @Test
    public void shouldGetTwoPopularBook() {
        BookOrder reservedOrder = bookService.reserveBook(PREDEFINED_POLISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
        bookOrderRepository.save(reservedOrder);
        BookOrder reservedOrder2 = bookService.reserveBook(PREDEFINED_IRISH_BOOK.getId(), TestData.PREDEFINED_SIMPLE_USER.getId());
        bookOrderRepository.save(reservedOrder2);
        BookOrder reservedOrder3 = bookService.reserveBook(PREDEFINED_IRISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
        bookOrderRepository.save(reservedOrder3);
        BookOrder reservedOrder4 = bookService.reserveBook(PREDEFINED_IRISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
        bookOrderRepository.save(reservedOrder4);
        BookOrder reservedOrder5 = bookService.reserveBook(PREDEFINED_IRISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
        bookOrderRepository.save(reservedOrder5);

        Collection<BookPopularity> books = bookRepository.findMostReadBooks(5);
        Assert.assertEquals(books.size(), 2);
        // PREDEFINED_IRISH_BOOK should located in first place
        Assert.assertEquals(books.iterator().next().getBook().getId(), PREDEFINED_IRISH_BOOK.getId());
    }

    @Test
    public void shouldPutRating() {
        bookService.evaluateBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId(), 2);
        int rating = bookService.findBookRating(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        Assert.assertEquals(rating, 2);
    }

    @Test
    public void shouldChangeRating() {
        bookService.evaluateBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId(), 2);
        bookService.evaluateBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId(), 5);
        int rating = bookService.findBookRating(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        Assert.assertEquals(rating, 5);
    }

    @Test
    public void shouldRemoveRating() {
        bookService.evaluateBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId(), 2);
        bookService.evaluateBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId(), 0);
        int rating = bookService.findBookRating(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        Assert.assertEquals(rating, 0);
    }

    @Test
    public void shouldMakeRatingTable() {
        bookService.evaluateBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_POLISH_BOOK.getId(), 4);
        bookService.evaluateBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId(), 5);
        bookService.evaluateBook(PREDEFINED_ADMIN_USER.getId(), PREDEFINED_IRISH_BOOK.getId(), 1);
        Collection<BookPopularity> mostRatedBooks = bookService.findMostRatedBooks(5);
        Assert.assertEquals(mostRatedBooks.size(), 2);
        Iterator<BookPopularity> iterator = mostRatedBooks.iterator();
        Assert.assertEquals(iterator.next().getBook().getId(), PREDEFINED_POLISH_BOOK.getId());
        // multiple rated books' rating calculated correctly
        Assert.assertEquals(iterator.next().getValue(), 3, 0);
    }
}
