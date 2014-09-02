package com.epam.library.mock.bean;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.maciejkizlich.interview.service.AdminService;
import pl.maciejkizlich.interview.service.BookService;
import pl.maciejkizlich.interview.service.OrderService;
import pl.maciejkizlich.interview.service.UserService;

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
