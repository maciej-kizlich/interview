package com.epam.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.epam.library.AbstractMvcIntegrationTest;
import com.epam.library.TestData;
import com.epam.library.user.db.WithDbUser;

public class AdminControllerTest extends AbstractMvcIntegrationTest{

	@Test
    @WithDbUser(TestData.PRIMARY_USER_NAME)
    public void shouldShowSettings() throws Exception {

        mockMvc.perform(
                get("/admin/showSettings")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/showSettings"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("configs"))
        ;
    }
	
	@Test
    @WithDbUser(TestData.PRIMARY_USER_NAME)
    public void shouldEditSettings() throws Exception {
		mockMvc.perform(
                post("/admin/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("max.loan.period", "50")     
                .param("max.reservation.period", "50"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/showSettings"))
                .andExpect(model().hasNoErrors())
        ;
    }
}
