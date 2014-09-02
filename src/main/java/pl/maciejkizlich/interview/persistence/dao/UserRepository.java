package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Authority;
import pl.maciejkizlich.interview.persistence.model.User;


public interface UserRepository extends  ModelRepository<User>{


    User findUserByLogin(String login);

    Collection<Authority> findAuthorities(Collection<String> authorities);

	Collection<User> findUsersByIds(Collection<Long> jobDatasource);
	
	Collection<User> findUsersWithOvertimeOrders();

}
