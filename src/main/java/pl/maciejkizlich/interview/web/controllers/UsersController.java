package pl.maciejkizlich.interview.web.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.persistence.model.UserMessage;
import pl.maciejkizlich.interview.persistence.model.UsersList;
import pl.maciejkizlich.interview.security.UserPrincipal;
import pl.maciejkizlich.interview.services.UserService;

@Controller
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/usersList", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String listUsers(Map<String, Object> model) {

		UsersList users = new UsersList();
		users.getUsersList().addAll(userService.findAllUsers());
		model.put("users", users);
		return "user/usersList";
	}

	@RequestMapping(value = "/showDetails/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String showUserDetails(@PathVariable(value = "userId") Long userId, ModelMap model) {
		User user = userService.findUser(userId);
		model.put("user", user);
		return "user/userDetails";
	}

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public String myProfile(ModelMap model) {
		final long userId = UserPrincipal.getLoggedUserId();
		User user = userService.findUser(userId);
		model.put("user", user);
		return "user/userDetails";
	}

	@RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editUser(@PathVariable(value = "userId") Long userId, Map<String, Object> model) {

		User user = userService.findUser(userId);

		model.put("user", user);

		return "user/userEdit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editUser(@ModelAttribute("user") User user, @RequestParam String[] stringAuthorities) {
		if (user.getId() != null) {
			userService.updateUser(user, stringAuthorities);
		} else {
			userService.registerUser(user);
		}

		return "redirect:/user/usersList";
	}
	
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public String getAllMessages(Map<String, Object> model) {
		final long userId = UserPrincipal.getLoggedUserId();
		Collection<UserMessage> findAllUserMessages = userService.findAllUserMessages(userId, false);
		
		model.put("messages", findAllUserMessages);
		return "user/showMessages";

	}
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public String sendMessage(@RequestParam String topic, @RequestParam String receiver, @RequestParam String body){

		final long userId = UserPrincipal.getLoggedUserId();
		
		userService.saveMessage(topic, receiver, body, userId);
		
		return "redirect:/user/messages";
	}
	
	@RequestMapping(value = "/pollMsg", method = RequestMethod.GET)
	public @ResponseBody Integer pollMsg() {
		final long userId = UserPrincipal.getLoggedUserId();
		Collection<UserMessage> findAllUserMessages = userService.findAllUserMessages(userId, true);
		return findAllUserMessages.size();
	}
}
