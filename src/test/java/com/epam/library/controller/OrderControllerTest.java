package com.epam.library.controller;

import com.epam.library.AbstractMvcIntegrationTest;
import com.epam.library.TestData;
import com.epam.library.user.db.WithDbUser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest extends AbstractMvcIntegrationTest {
    private final static Logger LOG = LoggerFactory.getLogger(OrderControllerTest.class);

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    @WithDbUser(TestData.SIMPLE_USER_NAME)
    public void userCanAccessMyOrdersPage() throws Exception {
        mockMvc.perform(
                get("/orders/myOrders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("orders/manage"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("orders"))
        ;
    }

    @Test
    @WithDbUser(TestData.SIMPLE_USER_NAME)
    public void userCannotAccessAllOrdersPage() throws Exception {
        mockMvc.perform(
                get("/orders/allOrders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(view().name("exception"))
        ;
    }

    @Test
    @WithDbUser(TestData.PRIMARY_USER_NAME)
    public void adminCanAccessAllOrdersPage() throws Exception {
        mockMvc.perform(
                get("/orders/allOrders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("orders/manage"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("orders"))
        ;
    }
}
