package com.epam.library.controller;

import com.epam.library.AbstractMvcIntegrationTest;
import com.epam.library.TestData;
import com.epam.library.user.db.WithDbUser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest extends AbstractMvcIntegrationTest {

    private final static Logger LOG = LoggerFactory.getLogger(BookControllerTest.class);


    @Test
    @WithDbUser(TestData.PRIMARY_USER_NAME)
    public void reserveBookSuccessfully() throws Exception {

        mockMvc.perform(
                post("/books/reserve")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bookId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("books/bookReserved"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("book"))
        ;
    }

    @Test
    @WithDbUser(TestData.PRIMARY_USER_NAME)
    public void borrowBookSuccessfully() throws Exception {


        mockMvc.perform(
                post("/books/borrow")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bookId", "1")
                        .param("borrowingUserId", String.valueOf(TestData.PREDEFINED_ADMIN_USER.getId()))
                        .param("orderId", "1"))

                           .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("books/bookBorrowed"))
                .andExpect(model().hasNoErrors())

                .andExpect(model().attributeExists("book"))

        ;
    }

}