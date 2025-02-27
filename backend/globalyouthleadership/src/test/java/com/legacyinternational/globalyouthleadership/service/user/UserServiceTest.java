package com.legacyinternational.globalyouthleadership.service.user;

import com.legacyinternational.globalyouthleadership.adapter.auth.RegisterRequest;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private LocalDateTime dateOfBirth = LocalDateTime.now();

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
        UserDetails userDetails = userService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("encryptedPassword123", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_UserNotExists_ShouldThrowUsernameNotFoundException() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(email)
        );

        assertEquals("User not found with email: " + email, exception.getMessage());
    }

    @Test
    void registerUser_ValidInput_ShouldSaveUser() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword123");

        userService.registerUser(request);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_EmailAlreadyExists_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser(request)
        );

        assertEquals("Email is already registered", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_PasswordIsHashed_ShouldNotMatchRawPassword() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword123");

        userService.registerUser(request);

        verify(userRepository).save(argThat(user -> !user.getPassword().equals(request.getPassword())));
    }

    @Test
    void registerUser_RepositoryThrowsException_ShouldThrowRuntimeException() {
        RegisterRequest request = new RegisterRequest("test@example.com", "StrongPassword123!", "John", "Doe", dateOfBirth);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword123");
        doThrow(new RuntimeException("Database error")).when(userRepository).save(any(User.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.registerUser(request)
        );

        assertEquals("Unable to save new user", exception.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

}