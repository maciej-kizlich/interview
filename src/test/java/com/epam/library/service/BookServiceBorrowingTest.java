package com.epam.library.service;

import static com.epam.library.TestData.PREDEFINED_ADMIN_USER;
import static com.epam.library.TestData.PREDEFINED_POLISH_BOOK;
import static org.junit.Assert.assertFalse;

import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookOrderStatus;
import pl.maciejkizlich.interview.persistence.model.User;

import com.epam.library.AbstractTest;
import com.epam.library.TestData;

public class BookServiceBorrowingTest extends AbstractTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private long reservedBookId;
    
    private int availableBookQuantity;
    
    @Before
    public void prepareBookReservation(){
    	BookOrder reservedOrder = bookService.reserveBook(PREDEFINED_POLISH_BOOK.getId(), TestData.PREDEFINED_ADMIN_USER.getId());
    	bookOrderRepository.save(reservedOrder);
    	reservedBookId = reservedOrder.getId();
    	availableBookQuantity = bookRepository.findById(PREDEFINED_POLISH_BOOK.getId()).getAvailableQuantity();
    }
    
    @Test
    public void shouldBorrowBook() {

        //when
        BookOrder bookOrder = bookService.borrowBook(TestData.PREDEFINED_ADMIN_USER.getId(), reservedBookId);

        //then
        Assert.assertNotNull(bookOrder.getId());
        Assert.assertEquals(PREDEFINED_POLISH_BOOK.getId(), bookOrder.getBook().getId());
        Assert.assertEquals(PREDEFINED_ADMIN_USER.getId(), bookOrder.getUser().getId());
        Assert.assertEquals(BookOrderStatus.BORROWED, bookOrder.getStatus());
        Assert.assertNull(bookOrder.getReturnedDate());
        Assert.assertNotNull(bookOrder.getExpectedReturnDate());

        Book reservedBook = bookRepository.findById(PREDEFINED_POLISH_BOOK.getId());
        Assert.assertEquals(availableBookQuantity, reservedBook.getAvailableQuantity());
    }
    
    @Test
    public void shouldReturnBook() {

    	//given
    	BookOrder bookOrder = bookService.borrowBook(TestData.PREDEFINED_ADMIN_USER.getId(), reservedBookId);
    	Book borrowedBook = bookOrder.getBook();
    	
        //then
    	bookService.returnBook(borrowedBook.getId(), TestData.PREDEFINED_ADMIN_USER.getId(), bookOrder.getId());

        //when
    	Assert.assertEquals(bookOrder.getStatus(), BookOrderStatus.RETURNED);
    	Assert.assertNotNull(bookOrder.getReturnedDate());
    	Assert.assertNotNull(bookOrder.getExpectedReturnDate());
    	
        Assert.assertEquals(availableBookQuantity + 1, borrowedBook.getAvailableQuantity());

    }
    
    @Test
    public void shouldUnlockUserReservationsWhenHeReturnsOvertimeBooks() {

        //given
        User user = userRepository.findById(PREDEFINED_ADMIN_USER.getId());
        user.setReservationBlocked(true);
        BookOrder order = bookOrderRepository.findById(PREDEFINED_POLISH_BOOK.getId());
        order.setUser(user);
        order.setExpectedReturnDate(new GregorianCalendar(2011,5,5).getTime());
        order.setStatus(BookOrderStatus.BORROWED);
        bookOrderRepository.save(order);
        
        //when
        bookService.returnBook(PREDEFINED_POLISH_BOOK.getId(), PREDEFINED_ADMIN_USER.getId(), order.getId());
        
        //then
        assertFalse(user.isReservationBlocked());
        
    }
}
