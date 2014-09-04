package pl.maciejkizlich.interview.web.controllers;

import java.util.Collection;
import java.util.List;
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

import pl.maciejkizlich.interview.persistence.model.BookFavorite;
import pl.maciejkizlich.interview.persistence.model.BookFeedback;
import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.persistence.model.UsersList;
import pl.maciejkizlich.interview.security.UserPrincipal;
import pl.maciejkizlich.interview.service.BookService;
import pl.maciejkizlich.interview.service.UserService;

@Controller
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

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
		prepareProfile(userId, model);
		return "user/userDetails";
	}

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public String myProfile(ModelMap model) {
		final long userId = UserPrincipal.getLoggedUserId();
		prepareProfile(userId, model);
		return "user/userDetails";
	}

	private void prepareProfile(long userId, ModelMap model) {
		assert model != null;
		User user = userService.findUser(userId);
		model.put("user", user);

		List<BookFeedback> bookFeedbackList = userService.findUserFeedback(userId);
		model.put("feedback", bookFeedbackList);

		Collection<BookFavorite> favorites = bookService.getFavorites(userId);
		model.put("favorites", favorites);
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
	public String editBook(@ModelAttribute("user") User user, @RequestParam String[] stringAuthorities) {
		if (user.getId() != null) {
			userService.updateUser(user, stringAuthorities);
		} else {
			userService.registerUser(user);
		}

		return "redirect:/user/usersList";
	}
}
