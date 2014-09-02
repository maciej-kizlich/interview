package com.epam.library.mock.bean;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.maciejkizlich.interview.persistence.dao.AdminRepository;
import pl.maciejkizlich.interview.persistence.dao.BookOrderRepository;
import pl.maciejkizlich.interview.persistence.dao.BookRepository;
import pl.maciejkizlich.interview.persistence.dao.TagRepository;
import pl.maciejkizlich.interview.persistence.dao.UserRepository;

@Configuration
class MockRepositoriesConfig {

    @Bean
    UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    TagRepository tagRepository() {
        return Mockito.mock(TagRepository.class);
    }


    @Bean
    BookRepository bookRepository() {
        return Mockito.mock(BookRepository.class);
    }

    @Bean
    BookOrderRepository bookOrderRepository() {
        return Mockito.mock(BookOrderRepository.class);
    }
    
    @Bean
    AdminRepository adminRepository() {
        return Mockito.mock(AdminRepository.class);
    }

}
