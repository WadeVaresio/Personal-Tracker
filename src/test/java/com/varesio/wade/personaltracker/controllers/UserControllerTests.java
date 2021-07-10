package com.varesio.wade.personaltracker.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test_isLoggedInFalse() throws Exception{
        MvcResult response = mockMvc.perform(get("/api/user/loggedIn").contentType("application/json")).
                andExpect(status().isOk()).andReturn();

        String responseBody = response.getResponse().getContentAsString();
        assertEquals("false", responseBody);
    }

    @Test
    @WithMockUser(value = "user")
    public void test_isLoggedInTrue() throws Exception{
        MvcResult response = mockMvc.perform(get("/api/user/loggedIn").contentType("application/json")).
                andExpect(status().isOk()).andReturn();

        String responseBody = response.getResponse().getContentAsString();
        assertEquals("true", responseBody);
    }
}
