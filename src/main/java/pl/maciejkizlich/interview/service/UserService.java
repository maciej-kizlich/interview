package pl.maciejkizlich.interview.service;

import java.util.Collection;
import java.util.List;

import pl.maciejkizlich.interview.persistence.model.BookFeedback;
import pl.maciejkizlich.interview.persistence.model.User;

public interface UserService {

    public void registerUser(User user);
    
    public Collection<User> findAllUsers();
    
    public User findUser(long userId);
    
    void updateUser(User user, String[] stringAuthorities);

    /**
     * Retrieves user's feedback list
     */
    List<BookFeedback> findUserFeedback(Long userId);
}
