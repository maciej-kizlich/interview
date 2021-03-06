package pl.maciejkizlich.interview.services;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.persistence.model.UserMessage;

public interface UserService {

    public void registerUser(User user);
    
    public Collection<User> findAllUsers();
    
    public User findUser(long userId);
    
    void updateUser(User user, String[] stringAuthorities);

    Collection<UserMessage> findAllUserMessages(long userId, boolean read);

	public void saveMessage(String topic, String receiver, String body, long userId);

}
