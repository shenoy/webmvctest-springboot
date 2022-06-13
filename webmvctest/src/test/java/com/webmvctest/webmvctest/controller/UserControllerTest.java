package com.webmvctest.webmvctest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webmvctest.webmvctest.entity.User;
import com.webmvctest.webmvctest.entity.UserResource;
import com.webmvctest.webmvctest.service.RegisterUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterUseCase registerUseCase;

    @Test
    void whenValidInput_thenReturns200() throws Exception {

        UserResource ur = new UserResource();
        ur.setName("howdy");
        ur.setEmail("hello@gmail.com");

        mockMvc.perform(post("/forums/{forumId}/register", 42L)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(ur)))
                .andExpect(status().isOk());

    }

    @Test
    void whenValidInput_thenMapsToBusinessModel() throws Exception {

        UserResource ur = new UserResource();
        ur.setName("howdy");
        ur.setEmail("hello@gmail.com");
        mockMvc.perform(post("/forums/{forumId}/register", 42L)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(ur)))
                .andExpect(status().isOk());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(registerUseCase, times(1)).registerUser(userCaptor.capture(), ArgumentMatchers.eq(true));
        assertEquals(userCaptor.getValue().getName(),"howdy");
        assertEquals(userCaptor.getValue().getEmail(),"hello@gmail.com");
    }

    @Test
    void whenNullValue_thenReturns400() throws Exception {
        UserResource user = new UserResource(null, "zaphod@galaxy.net");
        mockMvc.perform(post("/forums/{forumId}/register", 42L)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidInput_thenReturnsUserResource() throws Exception {
        UserResource user = new UserResource("zaphod", "zaphod@galaxy.net");

        MvcResult mvcResult =   mockMvc.perform(post("/forums/{forumId}/register", 42L)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();


        UserResource expectedResponseBody = user;
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(actualResponseBody ,
                objectMapper.writeValueAsString(expectedResponseBody));
    }

}