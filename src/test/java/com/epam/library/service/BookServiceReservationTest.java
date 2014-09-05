package com.epam.library.service;

import static com.epam.library.TestData.PREDEFINED_ADMIN_USER;
import static com.epam.library.TestData.PREDEFINED_POLISH_BOOK;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookOrderStatus;
import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.services.BookServiceImpl;

import com.epam.library.AbstractTest;
import com.epam.library.TestData;

public class BookServiceReservationTest extends AbstractTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReserveBook() {

        //when
        BookOrder bookOrder = bookService.reserveBook(PREDEFINED_POLISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());

        //then
        Assert.assertNotNull(bookOrder.getId());
        Assert.assertEquals(PREDEFINED_POLISH_BOOK.getId(), bookOrder.getBook().getId());
        Assert.assertEquals(PREDEFINED_ADMIN_USER.getId(), bookOrder.getUser().getId());
        Assert.assertEquals(BookOrderStatus.RESERVED, bookOrder.getStatus());
        Assert.assertNull(bookOrder.getReturnedDate());

        Book reservedBook = bookRepository.findById(PREDEFINED_POLISH_BOOK.getId());
        Assert.assertEquals(PREDEFINED_POLISH_BOOK.getAvailableQuantity() - 1, reservedBook.getAvailableQuantity());
    }


    @Test
    public void shouldFailBecauseOfNoAvailableBooks() {

        //given
        Book bookToReserve = bookRepository.findById(PREDEFINED_POLISH_BOOK.getId());
        bookToReserve.setAvailableQuantity(0);

        expectedException.expectMessage(BookServiceImpl.BOOK_UNAVAILABLE_ERROR);

        //when
        bookService.reserveBook(PREDEFINED_POLISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
    }

    @Test
    public void shouldFailBecauseBookIsDeleted() {

        //given
        Book bookToReserve = bookRepository.findById(PREDEFINED_POLISH_BOOK.getId());
        bookToReserve.setRemoved(true);

        expectedException.expectMessage(BookServiceImpl.BOOK_DELETED_ERROR);

        //when
        bookService.reserveBook(PREDEFINED_POLISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
    }
    
    @Test
    public void shouldFailBecauseUserHaveReservationsBlocked() {

        //given
        User user = userRepository.findById(PREDEFINED_ADMIN_USER.getId());
        user.setReservationBlocked(true);

        //then
        expectedException.expectMessage(BookServiceImpl.USER_RESERVATIONS_BLOCKED);

        //when
        bookService.reserveBook(PREDEFINED_POLISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
    }
}
