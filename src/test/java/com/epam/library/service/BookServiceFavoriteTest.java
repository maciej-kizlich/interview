package com.epam.library.service;

import com.epam.library.AbstractTest;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pl.maciejkizlich.interview.persistence.model.BookFavorite;
import pl.maciejkizlich.interview.service.BookService;
import static com.epam.library.TestData.*;

/**
 * Created by Levon_Movsesyan on 8/22/2014.
 */
public class BookServiceFavoriteTest extends AbstractTest {

    @Autowired
    private BookService bookService;

    @Test
    public void scenario() {
        bookService.addToFavorites(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        boolean isFavorite = bookService.isFavoriteBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        Assert.assertEquals(isFavorite, true);
        Collection<BookFavorite> favorites = bookService.getFavorites(PREDEFINED_SIMPLE_USER.getId());
        Assert.assertEquals(favorites.size(), 1);
        bookService.removeFromFavorites(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        isFavorite = bookService.isFavoriteBook(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        Assert.assertEquals(isFavorite, false);
        favorites = bookService.getFavorites(PREDEFINED_SIMPLE_USER.getId());
        Assert.assertEquals(favorites.size(), 0);
        bookService.addToFavorites(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        bookService.addToFavorites(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_POLISH_BOOK.getId());
        favorites = bookService.getFavorites(PREDEFINED_SIMPLE_USER.getId());
        Assert.assertEquals(favorites.size(), 2);
        bookService.removeFromFavorites(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_IRISH_BOOK.getId());
        bookService.removeFromFavorites(PREDEFINED_SIMPLE_USER.getId(), PREDEFINED_POLISH_BOOK.getId());
        favorites = bookService.getFavorites(PREDEFINED_SIMPLE_USER.getId());
        Assert.assertEquals(favorites.size(), 0);

    }
}
