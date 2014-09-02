package com.epam.library;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.User;

public class TestData {

    public static final String PRIMARY_USER_NAME = "library@epam.com";

    public static final String SIMPLE_USER_NAME = "simpleuser@epam.com";


    public static final User PREDEFINED_ADMIN_USER;

    public static final User PREDEFINED_SIMPLE_USER;

    public static final Book PREDEFINED_POLISH_BOOK;

    public static final Book PREDEFINED_IRISH_BOOK;

    public static final long PREDEFINED_ORDER_ID;


    static{
        PREDEFINED_ADMIN_USER = new User();
        PREDEFINED_ADMIN_USER.setId(1L);
        PREDEFINED_ADMIN_USER.setUsername("library@epam.com");
        PREDEFINED_ADMIN_USER.setPassword("123456");


        PREDEFINED_SIMPLE_USER = new User();
        PREDEFINED_SIMPLE_USER.setId(2L);
        PREDEFINED_SIMPLE_USER.setUsername("simpleuser@epam.com");
        PREDEFINED_SIMPLE_USER.setPassword("123456");

        PREDEFINED_POLISH_BOOK = new Book();
        PREDEFINED_POLISH_BOOK.setId(1l);
        PREDEFINED_POLISH_BOOK.setTitle("Przygody Koziołka Matołka");
        PREDEFINED_POLISH_BOOK.setAuthor("polish guy józek ącki");
        PREDEFINED_POLISH_BOOK.setAvailableQuantity(101);

        PREDEFINED_IRISH_BOOK = new Book();
        PREDEFINED_IRISH_BOOK.setId(2l);
        PREDEFINED_IRISH_BOOK.setTitle("John and Michael Banim bibliography");
        PREDEFINED_IRISH_BOOK.setAuthor("John Banim");
        PREDEFINED_IRISH_BOOK.setAvailableQuantity(27);
        
        PREDEFINED_ORDER_ID = 666L;

    }

    public static class MockUserDefaults {
        public static final String DEFAULT_MOCK_ADMIN_USER_USERNAME = "adminuser@epam.com";
        public static final String DEFAULT_MOCK_SIMPLE_USER_USERNAME = "simpleuser@epam.com";
    }
}
