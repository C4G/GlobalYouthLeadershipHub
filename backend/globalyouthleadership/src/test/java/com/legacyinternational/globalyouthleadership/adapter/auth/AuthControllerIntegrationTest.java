package com.legacyinternational.globalyouthleadership.adapter.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legacyinternational.globalyouthleadership.service.authentication.JwtUtil;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLogin_SuccessfulAuthentication() throws Exception {
        // Given valid credentials
        String username = "admin";
        String password = "admin123";
        // Create request
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Perform request
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void testLogin_MissingUsername_ShouldReturn400() throws Exception {
        // Given missing username
        LoginRequest loginRequest = new LoginRequest(null, "password");

        // Perform request
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Username and Password are required"));
    }

    @Test
    void testLogin_MissingPassword_ShouldReturn400() throws Exception {
        // Given missing password
        LoginRequest loginRequest = new LoginRequest("admin", null);

        // Perform request
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Username and Password are required"));
    }

    @Test
    void testLogin_InvalidCredentials_ShouldReturn401() throws Exception {
        // Given invalid credentials
        LoginRequest loginRequest = new LoginRequest("admin", "wrongpassword");

        // Perform request
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid username or password"));
    }
}