package com.epam.library.user.db;

import com.epam.library.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextTestExcecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath:spring/production-all-config.xml"),
        @ContextConfiguration("classpath:spring-mvc/mvc-core-config.xml"),
})
@WebAppConfiguration
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExcecutionListener.class})
public class DbUserTest {

    private static final String ADMIN_USERNAME = "library@epam.com";
    private static final String SIMPLE_USER_USERNAME = "simpleuser@epam.com";

    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    UserDetailsService userDetailsService;

    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .defaultRequest(get("/").with(testSecurityContext()))
                .addFilters(springSecurityFilterChain)
                .build();

    }

    @Test
    @WithDbUser(ADMIN_USERNAME)
    public void testCorrectlyAuthenticatedAsAdminUser() throws Exception{
        mockMvc
                .perform(get("/admin")
                ).andDo(print())
                .andExpect(authenticated().withUsername(TestData.PREDEFINED_ADMIN_USER.getUsername()).withRoles("ADMIN", "USER"))
        ;
    }

    @Test
    @WithDbUser(SIMPLE_USER_USERNAME)
    public void testCorrectlyAuthenticatedAsSimpleUser() throws Exception{
        mockMvc
                .perform(get("/user")
                ).andDo(print())
                .andExpect(authenticated().withUsername(TestData.PREDEFINED_SIMPLE_USER.getUsername()).withRoles("USER"))
        ;
    }

    @Test
    @WithDbUser(ADMIN_USERNAME)
    public void requestAdminProtectedUrlWithAdmin() throws Exception{
        mockMvc
                .perform(get("/orders/allOrders")
                ).andDo(print())
                .andExpect(view().name("orders/manage"))
        ;
    }

    @Test
    @WithDbUser(SIMPLE_USER_USERNAME)
    public void requestUserProtectedUrlWithSimpleUser() throws Exception{
        mockMvc
                .perform(get("/orders/myOrders")
                ).andDo(print())
                .andExpect(view().name("orders/manage"))
        ;
    }

    @Test
    @WithDbUser(SIMPLE_USER_USERNAME)
    public void requestAdminProtectedUrlWithSimpleUser() throws Exception{
        mockMvc
                .perform(get("/orders/allOrders")
                ).andDo(print())
                .andExpect(view().name("exception"))
        ;
    }

}
