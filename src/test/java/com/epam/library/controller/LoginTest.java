package com.epam.library.controller;

import com.epam.library.AbstractMvcIntegrationTest;
import com.epam.library.TestData;
import org.junit.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginTest extends AbstractMvcIntegrationTest {



    @Test
    public void redirectsToLoginPage() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    public void anonymousUserAlwaysRedirectsToLoginPage() throws Exception {
        mockMvc
                .perform(get("/some_page"))
                .andExpect(redirectedUrl("http://localhost/user/login"));
    }

    @Test
    public void notAuthorized() throws Exception {
        mockMvc
                .perform(get("/doesnt_matter_what").with(httpBasic("user", "incorrectPassword")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void unauthorizedUserCanAccessLoginPage() throws Exception {
        mockMvc
                .perform(get("/user/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void unauthorizedUserCanAccessRegisterPage() throws Exception {
        mockMvc
                .perform(get("/user/register"))
                .andExpect(status().isOk());
    }

    @Test
    public void adminCanSeeAdminPage() throws Exception {
        mockMvc.
                perform(formLogin().loginProcessingUrl("/j_spring_security_check").user(TestData.PREDEFINED_ADMIN_USER.getUsername()).password("123456")).
                andDo(print()).
                andExpect(authenticated().withRoles("ADMIN", "USER"));
    }

    @Test
    public void authenticationFailed() throws Exception {
        mockMvc
                .perform(formLogin().loginProcessingUrl("/j_spring_security_check").user("user").password("invalid"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/user/login?error"))
                .andExpect(unauthenticated());
    }

    @Test
    public void authenticationSuccessful() throws Exception {
        mockMvc
                .perform(formLogin().loginProcessingUrl("/j_spring_security_check").user(TestData.PREDEFINED_ADMIN_USER.getUsername()).password("123456"))
                .andDo(print())
                .andExpect(redirectedUrl("/orders/allOrders"))
                .andExpect(authenticated().withUsername(TestData.PRIMARY_USER_NAME));
    }


}
