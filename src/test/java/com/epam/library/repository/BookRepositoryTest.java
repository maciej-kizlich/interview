package com.epam.library.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import pl.maciejkizlich.interview.persistence.model.Book;

import com.epam.library.AbstractTest;

/**
 *
 * @author Oleksandr_Kalnyi
 */
public class BookRepositoryTest extends AbstractTest {

    @Test
    @Transactional
    public void testCreateBook() {
        Book book = new Book();

        bookRepository.save(book);
        Assert.assertNotNull(book.getId());
    }
}
