package com.legacyinternational.globalyouthleadership.adapter.auth;

import com.legacyinternational.globalyouthleadership.service.authentication.JwtUtil;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_SuccessfulAuthentication() {
        // Given valid credentials
        String username = "admin";
        String password = "password";
        String jwtToken = "mocked-jwt-token";
        UserDetails userDetails = new User(username, password, Collections.emptyList());

        // Mock behavior
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(username, password));
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.generateToken(username)).thenReturn(jwtToken);

        LoginRequest loginRequest = new LoginRequest(username, password);
        // When calling login
        ResponseEntity<?> response = authController.login(loginRequest);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Map);
        assertEquals(jwtToken, ((Map<?, ?>) response.getBody()).get("token"));

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtUtil, times(1)).generateToken(username);
    }

    @Test
    void testLogin_InvalidCredentials_ShouldThrowException() {
        // Given invalid credentials
        String username = "admin";
        String password = "wrongpassword";

        // Mock authentication failure
        doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Exception exception = assertThrows(ResponseStatusException.class, () -> authController.login(new LoginRequest(username, password)));
//        ResponseEntity<?> result = authController.login(new LoginRequest(username, password));
        assertEquals("401 UNAUTHORIZED \"Invalid username or password\"", exception.getMessage());

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void testLogin_MissingUsername_ShouldReturn400() {
        // Given a request missing username
        LoginRequest invalidRequest = new LoginRequest(null, "password");

        // When & Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authController.login(invalidRequest)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Username and Password are required", exception.getReason());

        // Ensure no authentication attempts
        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void testLogin_MissingPassword_ShouldReturn400() {
        // Given a request missing password
        LoginRequest invalidRequest = new LoginRequest("admin", null);

        // When & Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authController.login(invalidRequest)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Username and Password are required", exception.getReason());

        // Ensure no authentication attempts
        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void testLogin_BothUsernameAndPasswordMissing_ShouldReturn400() {
        // Given an empty request
        LoginRequest invalidRequest = new LoginRequest(null, null);

        // When & Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authController.login(invalidRequest)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Username and Password are required", exception.getReason());

        // Ensure no authentication attempts
        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(jwtUtil);
    }
}