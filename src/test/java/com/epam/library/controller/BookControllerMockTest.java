package com.epam.library.controller;

import com.epam.library.AbstractMvcMockTest;
import com.epam.library.user.mock.annotation.WithMockSimpleUser;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.service.BookService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerMockTest extends AbstractMvcMockTest {

    private final static Logger LOG = LoggerFactory.getLogger(BookControllerMockTest.class);

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    BookService bookService;

    @Test
    @WithMockSimpleUser()
    public void reserveBookSuccessfully() throws Exception {

        long bookId = 5646;

        Book book = new Book();
        book.setId(bookId);

        BookOrder order = new BookOrder();
        order.setBook(book);

        when(bookService.reserveBook(bookId, 1)).thenReturn(order);


        mockMvc.perform(
                post("/books/reserve")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bookId", String.valueOf(bookId)) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("books/bookReserved"))
                .andExpect(model().hasNoErrors())

                .andExpect(model().attributeExists("book"));


    }




}