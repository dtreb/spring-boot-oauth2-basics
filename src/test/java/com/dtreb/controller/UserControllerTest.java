package com.dtreb.controller;

import com.dtreb.WithMockOAuth2Authority;
import com.dtreb.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Tests {@link UserController} related endpoints.
 *
 * @author dtreb
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @SpyBean
    private IUserService userService;

    @Mock
    private org.springframework.security.core.userdetails.User userDetailsUser;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /**
     * Checks if we can get current user info with provided OAuth credentials.
     * @throws Exception security layer exception
     */
    @Test
    @WithMockOAuth2Authority(authority = "ROLE_USER")
    public void testCurrentUser() throws Exception {
        when(userService.getUserDetailsUser()).thenReturn(userDetailsUser);
        mockMvc.perform(get("/api/user"));
        verify(userService).getUserDetailsUser();
    }

    /**
     * Checks if security layer throws exception if no OAuth credentials are provided.
     * @throws Exception security layer exception
     */
    @Test(expected = Exception.class)
    @WithMockOAuth2Authority(authority = "UNKNOWN")
    public void testCurrentUserUnauthorized() throws Exception {
        mockMvc.perform(get("/api/user"));
    }
}