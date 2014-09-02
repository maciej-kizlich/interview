package com.epam.library.controller;

import com.epam.library.AbstractMvcIntegrationTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest extends AbstractMvcIntegrationTest {

    private final static Logger LOG = LoggerFactory.getLogger(AuthControllerTest.class);


    @Test
    public void shouldShowRegisterForm() throws Exception {

        mockMvc.perform(
                get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void shouldFailForPasswordField() throws Exception {

        mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "someemail@domain.com")
                        .param("password", "X"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(unauthenticated());
    }

    @Test
    public void shouldRegisterSuccessfully() throws Exception {
        registerUserSuccessfully("someemail@somedomain.com");
    }

    @Test
    public void shouldFailRegistrationBecauseUserAlreadyExists() throws Exception {
        final String USER_NAME = "someuser@someDomain.com";
        registerUserSuccessfully(USER_NAME);

        mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", USER_NAME)
                        .param("password", "somepassword"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("exception"))
                .andExpect(model().attribute("uri", "/user/register"))
        ;

    }


    private void registerUserSuccessfully(String username) throws Exception {

        mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", username)
                        .param("password", "hardPassword_23sxcv"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(model().hasNoErrors())
                .andExpect(
                        authenticated()
                                .withAuthenticationName(username)
                                .withRoles("USER")
                );
    }

}