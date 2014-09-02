package pl.maciejkizlich.interview.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    UserDetailsService service;

    @Autowired
    UserService userService;

    @Autowired
    Validator userValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @RequestMapping(value = "user/register", method = RequestMethod.GET)
    public String showRegisterForm(ModelMap modelMap) {
        User newUser = new User();
        modelMap.put("user", newUser);
        return "user/register";
    }

    @RequestMapping(value = "user/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") @Validated User user, BindingResult userBinding, HttpServletRequest request) {
        if (userBinding.hasErrors()) {
            return "user/register";
        }

        userService.registerUser(user);
        UserDetails ud = service.loadUserByUsername(user.getUsername());
        String unencryptedPassword = user.getPassword();
        Authentication auth = new UsernamePasswordAuthenticationToken(ud, unencryptedPassword, ud.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        boolean isAdmin = ud.getAuthorities().stream()
                .anyMatch(
                        n -> n.getAuthority().equals("ROLE_ADMIN")
                );

        return isAdmin ? "redirect:/users" : "redirect:/";
    }


    @RequestMapping(value = "user/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              @RequestParam(value = "redirect", required = false) String redirect) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("user/login");
        model.addObject("redirect", redirect);

        return model;

    }

}
