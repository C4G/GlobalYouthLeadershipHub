package com.legacyinternational.globalyouthleadership.adapter.auth;

import com.legacyinternational.globalyouthleadership.service.authentication.JwtUtil;
import com.legacyinternational.globalyouthleadership.service.user.Role;
import com.legacyinternational.globalyouthleadership.service.user.User;
import com.legacyinternational.globalyouthleadership.service.user.UserServiceImpl;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private AuthController authController;

    private LocalDateTime dateOfBirth;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dateOfBirth = LocalDateTime.parse("1990-05-20T00:00:00Z", DateTimeFormatter.ISO_DATE_TIME);
    }

    @Test
    void testLogin_SuccessfulAuthentication() {
        // Given valid credentials
        String email = "admin@gmail.com";
        String password = "admin123";
        String jwtToken = "mocked-jwt-token";
        User user = new User(email, password, "firstName", "lastName", dateOfBirth, Role.ADMIN, false);


        // Mock behavior
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(email, password));
        when(userServiceImpl.loadUserByUsername(email)).thenReturn(user);
        when(jwtUtil.generateToken(email)).thenReturn(jwtToken);

        LoginRequest loginRequest = new LoginRequest(email, password);
        // When calling login
        ResponseEntity<?> response = authController.login(loginRequest);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof LoginResponse);
        assertEquals(jwtToken, ((LoginResponse) response.getBody()).getToken());
        assertEquals(true, ((LoginResponse) response.getBody()).getIsVerified());

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken(email, password));
        verify(userServiceImpl, times(1)).loadUserByUsername(email);
        verify(jwtUtil, times(1)).generateToken(email);
    }

    @Test
    void testLogin_InvalidCredentials_ShouldThrowException() {
        // Given invalid credentials
        String username = "admin@gmail.com";
        String password = "wrongpassword";

        // Mock authentication failure
        doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Exception exception = assertThrows(ResponseStatusException.class, () -> authController.login(new LoginRequest(username, password)));
//        ResponseEntity<?> result = authController.login(new LoginRequest(username, password));
        assertEquals("401 UNAUTHORIZED \"Invalid Email or password\"", exception.getMessage());

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(userServiceImpl, never()).loadUserByUsername(anyString());
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
        assertEquals("Email and Password are required", exception.getReason());

        // Ensure no authentication attempts
        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(userServiceImpl);
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
        assertEquals("Email and Password are required", exception.getReason());

        // Ensure no authentication attempts
        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(userServiceImpl);
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
        assertEquals("Email and Password are required", exception.getReason());

        // Ensure no authentication attempts
        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(userServiceImpl);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void register_ValidInput_ShouldReturnSuccessMessage() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);

        ResponseEntity<?> response = authController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", ((Map<?, ?>) response.getBody()).get("message"));
        verify(userServiceImpl, times(1)).registerUser(request);
    }

    @Test
    void register_InvalidEmail_ShouldReturnBadRequest() {
        RegisterRequest request = new RegisterRequest("invalid-email", "password", "John", "Doe", dateOfBirth);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authController.register(request)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Email is required and must be a valid email address", exception.getReason());
    }

    @Test
    void register_NullPassword_ShouldReturnBadRequest() {
        RegisterRequest request = new RegisterRequest("test@example.com", null, "John", "Doe", dateOfBirth);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authController.register(request)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Password is required and must not be empty", exception.getReason());
    }

    @Test
    void register_UserServiceThrowsException_ShouldReturnBadRequest() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        doThrow(new IllegalArgumentException("Email already in use")).when(userServiceImpl).registerUser(request);

        ResponseEntity<?> response = authController.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already in use", ((Map<?, ?>) response.getBody()).get("error"));
    }
}