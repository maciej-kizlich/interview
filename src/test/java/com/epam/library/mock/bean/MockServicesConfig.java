package com.epam.library.mock.bean;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.maciejkizlich.interview.services.AdminService;
import pl.maciejkizlich.interview.services.BookService;
import pl.maciejkizlich.interview.services.OrderService;
import pl.maciejkizlich.interview.services.UserService;

@Configuration
class MockServicesConfig {

    @Bean
    BookService bookService() {
        return Mockito.mock(BookService.class);
    }

    @Bean
    UserService userService(){
        return Mockito.mock(UserService.class);
    }

    @Bean()
    OrderService orderService(){
        return Mockito.mock(OrderService.class);
    }
    
    @Bean()
    AdminService adminService(){
        return Mockito.mock(AdminService.class);
    }




}
