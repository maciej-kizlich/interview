package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Authority;
import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.persistence.model.UserMessage;


public interface UserRepository extends ModelRepository<User>{

	void saveUserMessage(UserMessage msg);

    User findUserByLogin(String login);

    Collection<Authority> findAuthorities(Collection<String> authorities);

	Collection<User> findUsersByIds(Collection<Long> jobDatasource);
	
	Collection<User> findUsersWithOvertimeOrders();
	
	Collection<UserMessage> findAllUserMessages(long userId, boolean read);

}
