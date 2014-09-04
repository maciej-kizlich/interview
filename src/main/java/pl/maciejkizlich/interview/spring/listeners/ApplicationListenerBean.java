package pl.maciejkizlich.interview.spring.listeners;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import pl.maciejkizlich.interview.persistence.dao.UserRepository;
import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.security.UserPrincipal;

public class ApplicationListenerBean implements ApplicationListener<ApplicationEvent> {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent authenticationEvent = (AuthenticationSuccessEvent) event;
			
			UserPrincipal principal = (UserPrincipal) authenticationEvent.getAuthentication().getPrincipal();
			
			User user = userRepository.findById(principal.getUserId());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
			
		}

	}
}