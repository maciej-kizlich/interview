package pl.maciejkizlich.interview.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.maciejkizlich.interview.mail.MailProvider;
import pl.maciejkizlich.interview.mail.MailerConfig;
import pl.maciejkizlich.interview.mail.templates.EMAIL_TEMPLATES;
import pl.maciejkizlich.interview.persistence.dao.UserRepository;
import pl.maciejkizlich.interview.persistence.model.Authority;
import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.persistence.model.UserMessage;
import pl.maciejkizlich.interview.persistence.model.UserRole;
import pl.maciejkizlich.interview.utils.TimeProvider;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimeProvider timeProvider;
    
    @Autowired
    private MailProvider mailProvider;
    
    @Override
    public void registerUser(User user) {
        String password = user.getPassword();
        user.setPassword(encryptPassword(password));
        user.enable();
        
        Date currentDate = timeProvider.getCurrentDate().toDate();
        
        user.setUserCreationDate(currentDate);
        user.setLastLoginDate(currentDate);
        user = userRepository.save(user);

        attachAutorities(user, new String[]{UserRole.ROLE_USER});
        
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("name", user.getUsername());
        
        MailerConfig emailConfig = new MailerConfig.EmailBuilder(user.getUsername()).template(EMAIL_TEMPLATES.USER_REGISTRATION).supplyVariables(valuesMap).build();
        mailProvider.sendMail(emailConfig);

    }

    private String encryptPassword(String password) {
    	return encoder.encode(password);
    }

	@Override
	public Collection<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUser(long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public void updateUser(User user, String[] stringAuthorities) {

		User oldUser = userRepository.findById(user.getId());

		attachAutorities(user, stringAuthorities);

		if (null == user.getPassword() || user.getPassword().equals("")) {
			user.setPassword(oldUser.getPassword());
		} else {
			String encodedPassword = encryptPassword(user.getPassword());
			user.setPassword(encodedPassword);
		}

		user.setUserCreationDate(timeProvider.getCurrentDate().toDate());

		userRepository.save(user);
	}

    private void attachAutorities(User user, String[] stringAuthorities) {
		Collection<Authority> userAuthorities = userRepository.findAuthorities(
                Arrays.asList(stringAuthorities)
        );

        for (Authority auth : userAuthorities) {
            user.addAuthority(auth);
        }
	}

	@Override
	public Collection<UserMessage> findAllUserMessages(long userId, boolean read) {
		return userRepository.findAllUserMessages(userId, read);
	}

	@Override
	public void saveMessage(String topic, String receiver, String body, long userId) {

		User sender = userRepository.findById(userId);
		
		UserMessage message = new UserMessage();
		
		message.setFromUser(sender);
		message.setMessage(body);
		message.setMessageDate(timeProvider.getCurrentTime().toDate());
		message.setTopic(topic);
		
		User toUser = userRepository.findUserByLogin(receiver);
		
		message.setToUser(toUser);
		
		userRepository.saveUserMessage(message);
		
	}
}
