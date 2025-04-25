package com.legacyinternational.globalyouthleadership.service.user;

import com.legacyinternational.globalyouthleadership.adapter.auth.RegisterRequest;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private final LocalDateTime dateOfBirth = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserExists_ShouldReturnUserDetails() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword("encryptedPassword123");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Act
        User userDetails = userServiceImpl.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("encryptedPassword123", userDetails.getPassword());
        assertTrue(userDetails.isVerified());
    }

    @Test
    void loadUserByUsername_UserExists_ShouldReturnUserDetails_isVerifiedFalse() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword("encryptedPassword123");
        mockUser.setRole(Role.PENDING_REVIEW);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Act
        User userDetails = userServiceImpl.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("encryptedPassword123", userDetails.getPassword());
        assertFalse(userDetails.isVerified());
    }

    @Test
    void loadUserByUsername_UserNotExists_ShouldThrowUsernameNotFoundException() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userServiceImpl.loadUserByUsername(email)
        );

        assertEquals("User not found with email: " + email, exception.getMessage());
    }

    @Test
    void registerUser_ValidInput_ShouldSaveUser() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword123");

        userServiceImpl.registerUser(request);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ValidInput_ShouldSaveUser_DefaultsToPendingReview() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword123");
        User user = new User(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName(), request.getDateOfBirth(), Role.PENDING_REVIEW, false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User expectedUser = userServiceImpl.registerUser(request);

        verify(userRepository, times(1)).save(any(User.class));
        assertFalse(expectedUser.isVerified());
        assertEquals("StrongPassword123!", expectedUser.getPassword());
        assertEquals("John", expectedUser.getFirstName());
        assertEquals("Doe", expectedUser.getLastName());
        assertEquals(dateOfBirth, expectedUser.getDateOfBirth());
        assertEquals(Role.PENDING_REVIEW, expectedUser.getRole());
        assertEquals("test@example.com", expectedUser.getEmail());
    }

    @Test
    void registerUser_EmailAlreadyExists_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                userServiceImpl.registerUser(request)
        );

        assertEquals("Email is already registered", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_PasswordIsHashed_ShouldNotMatchRawPassword() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword123");

        userServiceImpl.registerUser(request);

        verify(userRepository).save(argThat(user -> !user.getPassword().equals(request.getPassword())));
    }

    @Test
    void registerUser_RepositoryThrowsException_ShouldThrowRuntimeException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword123");
        doThrow(new RuntimeException("Database error")).when(userRepository).save(any(User.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userServiceImpl.registerUser(request)
        );

        assertEquals("Unable to save new user", exception.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void verifyUser_SuccessfulVerification_ReturnsUpdatedUser() {
        User pendingUser = User.builder().id(1L).email("pending@example.com").firstName("Pending").lastName("User").role(Role.PENDING_REVIEW).dateOfBirth(LocalDateTime.MAX).build();
        User updatedUser = User.builder().id(1L).email("pending@example.com").firstName("Pending").lastName("User").role(Role.USER).dateOfBirth(LocalDateTime.MAX).build();

        when(userRepository.findByEmail("pending@example.com")).thenReturn(Optional.of(pendingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userServiceImpl.verifyUser("pending@example.com");

        assertEquals(Role.USER, result.getRole());
        verify(userRepository, times(1)).findByEmail("pending@example.com");
        verify(userRepository, times(1)).save(pendingUser);
    }

    @Test
    void verifyUser_UserNotFound_ThrowsBadRequest() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userServiceImpl.verifyUser("notfound@example.com"));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("User not found with email: notfound@example.com", exception.getReason());
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void verifyUser_UserNotPendingReview_ThrowsBadRequest() {
        User existingUser = User.builder().id(1L).email("user@example.com").firstName("User").lastName("One").role(Role.USER).dateOfBirth(LocalDateTime.MAX).build();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(existingUser));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userServiceImpl.verifyUser("user@example.com"));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("User is not pending review", exception.getReason());
        verify(userRepository, times(1)).findByEmail("user@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void verifyUser_SaveFails_ThrowsInternalServerError() {
        User pendingUser = User.builder().id(1L).email("fail@example.com").firstName("Fail").lastName("User").role(Role.PENDING_REVIEW).dateOfBirth(LocalDateTime.MAX).build();
        User failedUser = User.builder().id(1L).email("fail@example.com").firstName("Fail").lastName("User").role(Role.PENDING_REVIEW).dateOfBirth(LocalDateTime.MAX).build(); // Save doesn't change role

        when(userRepository.findByEmail("fail@example.com")).thenReturn(Optional.of(pendingUser));
        when(userRepository.save(any(User.class))).thenReturn(failedUser);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userServiceImpl.verifyUser("fail@example.com"));

        assertEquals(500, exception.getStatusCode().value());
        assertEquals("Verification of User failed", exception.getReason());
        verify(userRepository, times(1)).findByEmail("fail@example.com");
        verify(userRepository, times(1)).save(pendingUser);
    }

    @Test
    void resetPasswordToDefault_SuccessfulReset_ReturnsUpdatedUser() {
        String email = "test@example.com";
        LocalDateTime dob = LocalDateTime.of(1990, 5, 20, 0, 0);
        User user = User.builder().id(1L).email(email).firstName("John").lastName("Doe").dateOfBirth(dob).password("oldPassword").build();
        String expectedPassword = "doe05201990";
        String encodedPassword = "encodedPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(expectedPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userServiceImpl.resetPasswordToDefault(email);

        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).encode(expectedPassword);
        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);
    }

    @Test
    void resetPasswordToDefault_UserNotFound_ThrowsNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userServiceImpl.resetPasswordToDefault(email));

        assertEquals(404, exception.getStatusCode().value());
        assertEquals("User not found", exception.getReason());
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void resetPassword_SuccessfulReset_ReturnsUpdatedUser() {
        String email = "test@example.com";
        User user = User.builder().id(1L).email(email).password("encodedOldPassword").build();
        String currentPassword = "oldPassword";
        String newPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(currentPassword, "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userServiceImpl.resetPassword(email, currentPassword, newPassword);

        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(currentPassword, "encodedOldPassword");
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);
    }

    @Test
    void resetPassword_UserNotFound_ThrowsNotFound() {
        String email = "notfound@example.com";
        String currentPassword = "oldPassword";
        String newPassword = "newPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userServiceImpl.resetPassword(email, currentPassword, newPassword));

        assertEquals(404, exception.getStatusCode().value());
        assertEquals("User not found", exception.getReason());
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void resetPassword_InvalidCurrentPassword_ThrowsUnauthorized() {
        String email = "test@example.com";
        User user = User.builder().id(1L).email(email).password("encodedOldPassword").build();
        String currentPassword = "wrongPassword";
        String newPassword = "newPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(currentPassword, "encodedOldPassword")).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userServiceImpl.resetPassword(email, currentPassword, newPassword));

        assertEquals(401, exception.getStatusCode().value());
        assertEquals("Invalid current password", exception.getReason());
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(currentPassword, "encodedOldPassword");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void resetPassword_EmptyNewPassword_ThrowsBadRequest() {
        String email = "test@example.com";
        User user = User.builder().id(1L).email(email).password("encodedOldPassword").build();
        String currentPassword = "oldPassword";
        String newPassword = "   ";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(currentPassword, "encodedOldPassword")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userServiceImpl.resetPassword(email, currentPassword, newPassword));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("New password cannot be empty", exception.getReason());
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(currentPassword, "encodedOldPassword");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void requestPasswordReset_UserExists_ReturnsUpdatedUser() {
        String email = "test@example.com";
        User user = User.builder().id(1L).email(email).resetRequired(false).build();
        User updatedUser = User.builder().id(1L).email(email).resetRequired(true).build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userServiceImpl.requestPasswordReset(email);

        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(user);
        assertTrue(result.isResetRequired());
        assertEquals(email, result.getEmail());
    }

    @Test
    void requestPasswordReset_UserNotFound_ThrowsNotFound() {
        String email = "notfound@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                userServiceImpl.requestPasswordReset(email));

        assertEquals(404, exception.getStatusCode().value());
        assertEquals("User not found", exception.getReason());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void requestPasswordReset_SaveFails_ThrowsRuntimeException() {
        String email = "test@example.com";
        User user = User.builder().id(1L).email(email).resetRequired(false).build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userServiceImpl.requestPasswordReset(email));

        assertEquals("Unable to mark user for password reset", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(user);
    }
}