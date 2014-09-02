package com.epam.library.controller;

import com.epam.library.AbstractMvcIntegrationTest;
import com.epam.library.TestData;
import com.epam.library.user.db.WithDbUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsersControllerTest extends AbstractMvcIntegrationTest {

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    @WithDbUser(TestData.PRIMARY_USER_NAME)
    public void shouldListUsers() throws Exception {
        mockMvc.perform(
                get("/user/usersList")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/usersList"))
                .andExpect(model().hasNoErrors())

                .andExpect(model().attributeExists("users"))
        ;
    }

}