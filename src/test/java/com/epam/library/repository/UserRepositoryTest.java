package com.epam.library.repository;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.maciejkizlich.interview.persistence.model.Authority;
import pl.maciejkizlich.interview.persistence.model.UserRole;

import com.epam.library.AbstractTest;


public class UserRepositoryTest extends AbstractTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetOneAuthority() {
        Collection<Authority> authorities = userRepository.findAuthorities(Arrays.asList(UserRole.ROLE_USER));
        Assert.assertEquals(1, authorities.size());
    }

    @Test
    public void testGetTwoAuthorities() {
        Collection<Authority> authorities = userRepository.findAuthorities(Arrays.asList(UserRole.ROLE_USER, UserRole.ROLE_ADMIN));
        Assert.assertEquals(2, authorities.size());
    }
}
