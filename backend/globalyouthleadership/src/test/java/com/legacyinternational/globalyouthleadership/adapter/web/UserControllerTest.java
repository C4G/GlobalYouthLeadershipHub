package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.ApiResponse;
import com.legacyinternational.globalyouthleadership.service.UserService;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void resetPassword_SuccessfulReset_ReturnsOk() {
        String email = "test@example.com";
        String currentPassword = "oldPassword";
        String newPassword = "newPassword";
        PasswordResetByUserRequest request = new PasswordResetByUserRequest(currentPassword, newPassword);
        Principal principal = mock(Principal.class);
        User mockUser = User.builder().email(email).password(passwordEncoder.encode("oldPassword")).build();


        when(principal.getName()).thenReturn(email);
        when(userService.resetPassword(email, currentPassword, newPassword)).thenReturn(mockUser);


        ResponseEntity<ApiResponse> response = userController.resetPassword(request, principal);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).resetPassword(email, currentPassword, newPassword);
    }

    @Test
    void resetPassword_UserServiceThrowsException_ThrowsSameException() {
        String email = "test@example.com";
        String currentPassword = "oldPassword";
        String newPassword = "newPassword";
        PasswordResetByUserRequest request = new PasswordResetByUserRequest(currentPassword, newPassword);
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn(email);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request"))
                .when(userService).resetPassword(email, currentPassword, newPassword);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userController.resetPassword(request, principal));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Invalid request", exception.getReason());
        verify(userService, times(1)).resetPassword(email, currentPassword, newPassword);
    }
}
