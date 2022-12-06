package com.holyvia.fashionblog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holyvia.fashionblog.dtos.UserLoginDto;
import com.holyvia.fashionblog.dtos.UserSignupDto;
import com.holyvia.fashionblog.enums.Gender;
import com.holyvia.fashionblog.enums.Role;
import com.holyvia.fashionblog.models.User;
import com.holyvia.fashionblog.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private UserService userService;


    @Test
    void signUpShouldReturnSuccessful() {
        try {
            User user = User.builder()
                    .user_id(2L)
                    .name("Look")
                    .email("look@gmail.com")
                    .gender(Gender.MALE)
                    .password("Banks1234")
                    .role(Role.ADMIN)
                    .build();
            UserSignupDto usd = UserSignupDto.builder()
                    .name("Look")
                    .email("look@gmail.com")
                    .gender(Gender.MALE)
                    .password("Bank1234")
                    .role(Role.ADMIN)
                    .build();
            when(userService.createUser(usd)).thenReturn(user);
            String requestBody = mapper.writeValueAsString(usd);
            mockMvc.perform(post("/api/v1/admin/signup",  42L)
                            .contentType("application/json").content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("Signup Successful"));
        }catch (Exception ce){
            ce.printStackTrace();
        }
    }

    @Test
    void loginUser() {
        try{
            User user = User.builder()
                    .user_id(2L)
                    .name("Look")
                    .email("look@gmail.com")
                    .gender(Gender.MALE)
                    .password("Banks1234")
                    .role(Role.ADMIN)
                    .build();
            UserLoginDto uld = UserLoginDto.builder()
                    .email("look@gmail.com")
                    .password("Bank1234")
                    .build();
            when(userService.loginUser(uld)).thenReturn(user);
            String requestBody = mapper.writeValueAsString(uld);
            mockMvc.perform(post("/api/v1/admin/login", 2L)
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Login Successful"));
        }catch(
                Exception ce) {
            ce.printStackTrace();
        }
    }

    @Test
    void logout() {
        try{
            when(userService.logoutUser()).thenReturn("Logged out");
            mockMvc.perform(get("/api/v1/admin/log-out"))
                    .andExpect(content().string("Logout Successful"))
                    .andExpect(status().isOk());
        }catch(
                Exception ce) {
            ce.printStackTrace();
        }
    }
}