package com.epam.library.util;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SecuredRequestPostProcessor implements RequestPostProcessor {


    String username;


    UserDetailsService userDetailsService;

    public SecuredRequestPostProcessor(String username, UserDetailsService userDetailsService) {
        this.username = username;
        this.userDetailsService = userDetailsService;
    }

    /**
     * @deprecated use @WithDbUser instead
     * @param mockRequest
     * @return
     */
    @Override
    @Deprecated()
    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest mockRequest) {

        setupSecurityContext();

        SecurityContextRepository repository = new HttpSessionSecurityContextRepository();

        HttpServletResponse response = new MockHttpServletResponse();
        HttpRequestResponseHolder requestResponseHolder = new HttpRequestResponseHolder(mockRequest, response);
        repository.loadContext(requestResponseHolder);

        HttpServletRequest request = requestResponseHolder.getRequest();
        response = requestResponseHolder.getResponse();

        repository.saveContext(SecurityContextHolder.getContext(), request, response);

        return mockRequest;
    }

    private void setupSecurityContext() {
        UserDetails user = userDetailsService.loadUserByUsername(username);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


    public static SecuredRequestPostProcessor securedRequestForUser(String username, UserDetailsService userDetailsService) {
        return new SecuredRequestPostProcessor(username, userDetailsService);
    }


}
