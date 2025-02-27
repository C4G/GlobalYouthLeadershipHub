package com.legacyinternational.globalyouthleadership.adapter.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class RegisterRequestTest {

    private LocalDateTime dateOfBirth = LocalDateTime.now();

    @Test
    void validateInput_ValidRequest_ShouldPass() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        assertDoesNotThrow(() -> RegisterRequest.validateInput(request));
    }

    @Test
    void validateInput_NullEmail_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest(null, "StrongPassword123!", "John", "Doe", dateOfBirth);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("Email is required and must be a valid email address", exception.getMessage());
    }

    @Test
    void validateInput_InvalidEmail_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("invalid-email", "StrongPassword123!", "John", "Doe", dateOfBirth);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("Email is required and must be a valid email address", exception.getMessage());
    }

    @Test
    void validateInput_EmptyFirstName_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "", "Doe", dateOfBirth);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("First name is required and must not be empty", exception.getMessage());
    }

    @Test
    void validateInput_NullFirstName_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", null, "Doe", dateOfBirth);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("First name is required and must not be empty", exception.getMessage());
    }

    @Test
    void validateInput_EmptyLastName_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "", dateOfBirth);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("Last name is required and must not be empty", exception.getMessage());
    }

    @Test
    void validateInput_NullLastName_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", null, dateOfBirth);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("Last name is required and must not be empty", exception.getMessage());
    }

    @Test
    void validateInput_NullDateOfBirth_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", null);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("Date of birth is required and must not be empty", exception.getMessage());
    }

    @Test
    void validateInput_EmptyPassword_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "", "John", "Doe", dateOfBirth);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("Password is required and must not be empty", exception.getMessage());
    }

    @Test
    void validateInput_NullPassword_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", null, "John", "Doe", dateOfBirth);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RegisterRequest.validateInput(request)
        );
        assertEquals("Password is required and must not be empty", exception.getMessage());
    }
}