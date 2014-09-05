package com.epam.library;

import java.util.Collection;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.maciejkizlich.interview.persistence.dao.BookOrderRepository;
import pl.maciejkizlich.interview.persistence.dao.BookRepository;
import pl.maciejkizlich.interview.persistence.dao.TagRepository;
import pl.maciejkizlich.interview.persistence.dao.UserRepository;
import pl.maciejkizlich.interview.services.BookService;
import pl.maciejkizlich.interview.services.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/production-all-config.xml"})
@Transactional
public abstract class AbstractTest {

	@Autowired
	protected BookService bookService;

	@Autowired
	protected BookRepository bookRepository;

	@Autowired
	protected BookOrderRepository bookOrderRepository;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
    protected UserService userService;
	
	@Autowired
	protected TagRepository tagRepository;

    //TODO - i am confused if this method should be here or somewhere else
    protected void assertNotNullAndNotZeroElements(Collection collection) {
        Assert.assertNotNull(collection);
        Assert.assertTrue(collection.size() > 0);
    }

}
