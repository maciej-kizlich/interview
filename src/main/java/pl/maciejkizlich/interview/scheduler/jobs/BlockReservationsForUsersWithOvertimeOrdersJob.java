package pl.maciejkizlich.interview.scheduler.jobs;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.scheduler.AbstractScheduledJob;

public class BlockReservationsForUsersWithOvertimeOrdersJob extends AbstractScheduledJob<Collection<User>> {

	@Override
	protected Collection<User> getJobDatasource() {
		Collection<User> results = userRepository.findUsersWithOvertimeOrders();
		return results;
	}

	@Override
	protected void executeJob(Collection<User> jobDatasource) {
		for (User user : jobDatasource) {
			user.setReservationBlocked(true);
			userRepository.save(user);
		}
	}
}
