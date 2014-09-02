package com.epam.library.service;

import static com.epam.library.TestData.PREDEFINED_IRISH_BOOK;
import static com.epam.library.TestData.PREDEFINED_POLISH_BOOK;
import static com.epam.library.TestData.PREDEFINED_SIMPLE_USER;

import org.junit.Assert;
import org.junit.Test;

import pl.maciejkizlich.interview.persistence.model.BookSearch;
import pl.maciejkizlich.interview.persistence.model.Books;

import com.epam.library.AbstractTest;

/**
 * Created by Levon_Movsesyan on 8/21/2014.
 */
public class BookServiceSearchingTest extends AbstractTest {

    @Test
    public void shouldGetSearchResult() {
        bookService.saveOrUpdateBook(PREDEFINED_IRISH_BOOK);
        bookService.saveOrUpdateBook(PREDEFINED_POLISH_BOOK);

        BookSearch bookSearch = new BookSearch();
        Books books = bookService.searchBook(bookSearch);
        Assert.assertEquals(books.size(), 2);

        bookSearch.setAuthor("ani");
        books = bookService.searchBook(bookSearch);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0).getBook(), PREDEFINED_IRISH_BOOK);

        bookSearch.setTitle("chae1");
        books = bookService.searchBook(bookSearch);
        Assert.assertEquals(books.size(), 0);

        bookSearch.setTitle("chae");
        books = bookService.searchBook(bookSearch);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0).getBook(), PREDEFINED_IRISH_BOOK);

        bookSearch.setRating(4);
        bookService.evaluateBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId(), 3);
        books = bookService.searchBook(bookSearch);
        Assert.assertEquals(books.size(), 0);

        bookSearch.setRating(3);
        books = bookService.searchBook(bookSearch);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0).getBook(), PREDEFINED_IRISH_BOOK);
    }
}
