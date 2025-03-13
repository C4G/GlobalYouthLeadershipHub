package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.UserResponse;
import com.legacyinternational.globalyouthleadership.service.UserService;
import com.legacyinternational.globalyouthleadership.service.user.Role;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    private List<User> mockUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUsers = List.of(
                new User(1L, "user1@example.com", "username", "password", "User", "One", LocalDateTime.MAX, Role.USER),
                new User(2L, "user2@example.com", "username", "password", "User", "Two", LocalDateTime.MAX, Role.PENDING_REVIEW),
                new User(3L, "admin@example.com", "username", "password", "Admin", "User", LocalDateTime.MAX, Role.ADMIN)
        );
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<List<UserResponse>> response = adminController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getPendingReviewUsers_ReturnsPendingUsers() {
        when(userService.getUsersByRole(Role.PENDING_REVIEW))
                .thenReturn(mockUsers.stream().filter(user -> user.getRole() == Role.PENDING_REVIEW).toList());

        ResponseEntity<List<UserResponse>> response = adminController.getPendingReviewUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getUsersByRole(Role.PENDING_REVIEW);
    }

    @Test
    void getUserRoleUsers_ReturnsUsers() {
        when(userService.getUsersByRole(Role.USER))
                .thenReturn(mockUsers.stream().filter(user -> user.getRole() == Role.USER).toList());

        ResponseEntity<List<UserResponse>> response = adminController.getUserRoleUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getUsersByRole(Role.USER);
    }

    @Test
    void getAdminRoleUsers_ReturnsAdminUsers() {
        when(userService.getUsersByRole(Role.ADMIN))
                .thenReturn(mockUsers.stream().filter(user -> user.getRole() == Role.ADMIN).toList());

        ResponseEntity<List<UserResponse>> response = adminController.getAdminRoleUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getUsersByRole(Role.ADMIN);
    }

    @Test
    void verifyUser_SuccessfulVerification_ReturnsOk() {
        VerifyRequest verifyRequest = new VerifyRequest("test@example.com");

        ResponseEntity<ApiResponse> response = adminController.verifyUser(verifyRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test@example.com has been verified and updated successfully", response.getBody().getMessage());
        verify(userService, times(1)).verifyUser("test@example.com");
    }

    @Test
    void verifyUser_NullEmail_ThrowsBadRequest() {
        VerifyRequest verifyRequest = new VerifyRequest(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            adminController.verifyUser(verifyRequest);
        });

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Email is required", exception.getReason());
        verify(userService, never()).verifyUser(anyString());
    }

    @Test
    void verifyUser_EmptyEmail_ThrowsBadRequest() {
        VerifyRequest verifyRequest = new VerifyRequest("");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            adminController.verifyUser(verifyRequest);
        });

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Email is required", exception.getReason());
        verify(userService, never()).verifyUser(anyString());
    }

    @Test
    void verifyUser_InvalidEmail_ThrowsBadRequest() {
        VerifyRequest verifyRequest = new VerifyRequest("invalid-email");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            adminController.verifyUser(verifyRequest);
        });

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Invalid email", exception.getReason());
        verify(userService, never()).verifyUser(anyString());
    }
}