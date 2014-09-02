package com.epam.library.scheduler;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.scheduler.jobs.BlockReservationsForUsersWithOvertimeOrdersJob;

public class BlockReservationsForUsersWithOvertimeOrdersJobTest extends SchedulerTest {

	@InjectMocks
	private BlockReservationsForUsersWithOvertimeOrdersJob job = new BlockReservationsForUsersWithOvertimeOrdersJob();
	
	@Test
	public void shouldBlockUserReservations(){
		
		//given
		User u = new User();
		
		Collection<User> datasource = new ArrayList<User>();
		datasource.add(u);
		
		Mockito.when(userRepository.findUsersWithOvertimeOrders()).thenReturn(datasource);
		
	    //when
		job.initJob();

		//then
		assertTrue(u.isReservationBlocked());
		
	}
		
}
