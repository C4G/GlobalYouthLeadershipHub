package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.ApiResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.UserResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.VerifyRequest;
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
                new User(1L, "user1@example.com", "username", "password", "User", "One", LocalDateTime.MAX, Role.USER, false),
                new User(2L, "user2@example.com", "username", "password", "User", "Two", LocalDateTime.MAX, Role.PENDING_REVIEW, false),
                new User(3L, "admin@example.com", "username", "password", "Admin", "User", LocalDateTime.MAX, Role.ADMIN, false)
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

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.verifyUser(verifyRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Email is required", exception.getReason());
        verify(userService, never()).verifyUser(anyString());
    }

    @Test
    void verifyUser_EmptyEmail_ThrowsBadRequest() {
        VerifyRequest verifyRequest = new VerifyRequest("");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.verifyUser(verifyRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Email is required", exception.getReason());
        verify(userService, never()).verifyUser(anyString());
    }

    @Test
    void verifyUser_InvalidEmail_ThrowsBadRequest() {
        VerifyRequest verifyRequest = new VerifyRequest("invalid-email");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.verifyUser(verifyRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Invalid email", exception.getReason());
        verify(userService, never()).verifyUser(anyString());
    }

    @Test
    void promoteToAdmin_SuccessfulPromotion_ReturnsOk() {
        PromoteRequest promoteRequest = new PromoteRequest("user1@example.com");
        User promotedUser = new User(1L, "user1@example.com", "username", "password", "User", "One", LocalDateTime.MAX, Role.ADMIN, false);
        when(userService.promoteToAdmin(promoteRequest.getEmail())).thenReturn(promotedUser);

        ResponseEntity<ApiResponse> response = adminController.promoteToAdmin(promoteRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("user1@example.com has been promoted to admin", response.getBody().getMessage());
        verify(userService, times(1)).promoteToAdmin("user1@example.com");
    }

    @Test
    void promoteToAdmin_NullEmail_ThrowsBadRequest() {
        PromoteRequest promoteRequest = new PromoteRequest(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.promoteToAdmin(promoteRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Email is required", exception.getReason());
        verify(userService, never()).promoteToAdmin(anyString());
    }

    @Test
    void promoteToAdmin_InvalidEmail_ThrowsBadRequest() {
        PromoteRequest promoteRequest = new PromoteRequest("invalid-email");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.promoteToAdmin(promoteRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Invalid email", exception.getReason());
        verify(userService, never()).promoteToAdmin(anyString());
    }

    @Test
    void demoteToUser_SuccessfulDemotion_ReturnsOk() {
        DemoteRequest demoteRequest = new DemoteRequest("admin@example.com");
        User demotedUser = new User(3L, "admin@example.com", "username", "password", "Admin", "User", LocalDateTime.MAX, Role.USER, false);
        when(userService.demoteToUser(demoteRequest.getEmail())).thenReturn(demotedUser);

        ResponseEntity<ApiResponse> response = adminController.demoteToUser(demoteRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("admin@example.com has been demoted to regular user", response.getBody().getMessage());
        verify(userService, times(1)).demoteToUser("admin@example.com");
    }

    @Test
    void demoteToUser_NullEmail_ThrowsBadRequest() {
        DemoteRequest demoteRequest = new DemoteRequest(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.demoteToUser(demoteRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Email is required", exception.getReason());
        verify(userService, never()).demoteToUser(anyString());
    }

    @Test
    void demoteToUser_InvalidEmail_ThrowsBadRequest() {
        DemoteRequest demoteRequest = new DemoteRequest("invalid-email");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.demoteToUser(demoteRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Invalid email", exception.getReason());
        verify(userService, never()).demoteToUser(anyString());
    }

    @Test
    void resetPasswordByAdmin_SuccessfulReset_ReturnsOk() {
        String email = "test@example.com";
        PasswordResetByAdminRequest resetRequest = new PasswordResetByAdminRequest(email);
        User mockUser = User.builder().email(email).build();

        when(userService.resetPasswordToDefault(email)).thenReturn(mockUser);

        ResponseEntity<ApiResponse> response = adminController.resetPasswordToDefault(resetRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(email + "'s password reset to default successfully", response.getBody().getMessage());
        verify(userService, times(1)).resetPasswordToDefault(email);
    }

    @Test
    void resetPasswordByAdmin_NullEmail_ThrowsBadRequest() {
        PasswordResetByAdminRequest resetRequest = new PasswordResetByAdminRequest(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.resetPasswordToDefault(resetRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Invalid email", exception.getReason());
        verify(userService, never()).resetPasswordToDefault(anyString());
    }

    @Test
    void resetPasswordByAdmin_EmptyEmail_ThrowsBadRequest() {
        PasswordResetByAdminRequest resetRequest = new PasswordResetByAdminRequest("");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.resetPasswordToDefault(resetRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Invalid email", exception.getReason());
        verify(userService, never()).resetPasswordToDefault(anyString());
    }

    @Test
    void resetPasswordByAdmin_InvalidEmail_ThrowsBadRequest() {
        PasswordResetByAdminRequest resetRequest = new PasswordResetByAdminRequest("invalid-email");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> adminController.resetPasswordToDefault(resetRequest));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Invalid email", exception.getReason());
        verify(userService, never()).resetPasswordToDefault(anyString());
    }
}
