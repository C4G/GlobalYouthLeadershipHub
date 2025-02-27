package com.legacyinternational.globalyouthleadership.adapter.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void validateLoginRequest_ValidEmailAndPassword_ShouldPass() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
        assertDoesNotThrow(() -> LoginRequest.validateLoginRequest(loginRequest));
    }

    @Test
    void validateLoginRequest_NullPassword_ShouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", null);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                LoginRequest.validateLoginRequest(loginRequest)
        );
        assertEquals("Password is required and must not be empty", exception.getMessage());
    }

    @Test
    void validateLoginRequest_EmptyPassword_ShouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                LoginRequest.validateLoginRequest(loginRequest)
        );
        assertEquals("Password is required and must not be empty", exception.getMessage());
    }

    @Test
    void validateLoginRequest_NullEmail_ShouldThrowException() {
        LoginRequest loginRequest = new LoginRequest(null, "password123");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                LoginRequest.validateLoginRequest(loginRequest)
        );
        assertEquals("Email is required and must be a valid email address", exception.getMessage());
    }

    @Test
    void validateLoginRequest_EmptyEmail_ShouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("", "password123");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                LoginRequest.validateLoginRequest(loginRequest)
        );
        assertEquals("Email is required and must be a valid email address", exception.getMessage());
    }

    @Test
    void validateLoginRequest_InvalidEmail_ShouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("invalid-email", "password123");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                LoginRequest.validateLoginRequest(loginRequest)
        );
        assertEquals("Email is required and must be a valid email address", exception.getMessage());
    }

    @Test
    void validateLoginRequest_EmailWithInvalidDomain_ShouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("user@invalid_domain", "password123");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                LoginRequest.validateLoginRequest(loginRequest)
        );
        assertEquals("Email is required and must be a valid email address", exception.getMessage());
    }

    @Test
    void validateLoginRequest_EmailWithSpaces_ShouldThrowException() {
        LoginRequest loginRequest = new LoginRequest(" user@example.com ", "password123");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                LoginRequest.validateLoginRequest(loginRequest)
        );
        assertEquals("Email is required and must be a valid email address", exception.getMessage());
    }

    @Test
    void validateLoginRequest_EmailWithoutTld_ShouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("user@example", "password123");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                LoginRequest.validateLoginRequest(loginRequest)
        );
        assertEquals("Email is required and must be a valid email address", exception.getMessage());
    }
}