package com.epam.library.scheduler;

import org.joda.time.DateTime;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.transaction.PlatformTransactionManager;

import pl.maciejkizlich.interview.persistence.dao.AdminRepository;
import pl.maciejkizlich.interview.persistence.dao.AdminRepositoryImpl;
import pl.maciejkizlich.interview.persistence.dao.UserRepository;
import pl.maciejkizlich.interview.utils.TimeProvider;

import com.epam.library.testdoubles.FakeTimeProvider;
import com.epam.library.testdoubles.MockedTransactionManager;

public class SchedulerTest {

	@Spy
	protected PlatformTransactionManager txManager = new MockedTransactionManager();
	
	@Spy
	protected TimeProvider timeProvider = new FakeTimeProvider(new DateTime());
	
	@Spy
	protected AdminRepository adminRepository = new AdminRepositoryImpl();
	
	@Spy
	protected UserRepository userRepository = Mockito.mock(UserRepository.class);

	@Before public void initMocks() {
	       MockitoAnnotations.initMocks(this);
	   }
	}
