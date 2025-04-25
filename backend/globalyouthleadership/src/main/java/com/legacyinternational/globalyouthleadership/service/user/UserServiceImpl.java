package com.legacyinternational.globalyouthleadership.service.user;

import com.legacyinternational.globalyouthleadership.adapter.auth.RegisterRequest;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.UserRepository;
import com.legacyinternational.globalyouthleadership.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Overriden to leverage email instead of username
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    /**
     * Placed here for development -- will remove once database is in
     * TODO: REMOVE ME ONCE DB IS IN
     */
    @PostConstruct
    public void createAdminUser() {
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            User admin = new User("admin@gmail.com"
                    , passwordEncoder.encode("admin123")
                    , "firstName"
                    , "lastName"
                    , LocalDateTime.parse("1990-05-20T00:00:00Z", ISO_FORMAT)
                    , Role.ADMIN
                    , false);
            userRepository.save(admin);
            System.out.println("Admin user created: admin / admin123");
        }
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setDateOfBirth(LocalDateTime.parse(registerRequest.getDateOfBirth().toString(), ISO_FORMAT));
        newUser.setRole(Role.PENDING_REVIEW);

        try {
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Unable to save new user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User verifyUser(String email) {
        //check if user exists
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with email: " + email);
        }

        User userToUpdate = optionalUser.get();
        if(userToUpdate.getRole() != Role.PENDING_REVIEW) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not pending review");
        }

        userToUpdate.setRole(Role.USER);
        User updatedUser = userRepository.save(userToUpdate);
        if (updatedUser.getRole().equals(Role.USER)) {
            return updatedUser;
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Verification of User failed");
    }

    @Override
    public User promoteToAdmin(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }

        User userToPromote = optionalUser.get();
        if (userToPromote.getRole() == Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already an admin");
        }

        userToPromote.setRole(Role.ADMIN);
        User updatedUser = userRepository.save(userToPromote);

        if (updatedUser.getRole().equals(Role.ADMIN)) {
            return updatedUser;
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Promotion to admin failed");
    }

    @Override
    public User demoteToUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }

        User userToDemote = optionalUser.get();
        if (userToDemote.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already a regular user");
        }

        userToDemote.setRole(Role.USER);
        User updatedUser = userRepository.save(userToDemote);

        if (updatedUser.getRole().equals(Role.USER)) {
            return updatedUser;
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Demotion to user failed");
    }

    @Override
    public User resetPasswordToDefault(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String datePart = user.getDateOfBirth().format(formatter);
        String defaultPassword = user.getLastName().toLowerCase() + datePart;

        user.setPassword(passwordEncoder.encode(defaultPassword));
        user.setResetRequired(false);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Unable to reset password to default", e);
        }
    }

    @Override
    public User resetPassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid current password");
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password cannot be empty");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetRequired(false);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Unable to reset password", e);
        }
    }

    @Override
    public User requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setResetRequired(true);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Unable to mark user for password reset", e);
        }
    }

    public List<User> getUsersRequiringPasswordReset() {
        return userRepository.findByResetRequiredTrue();
    }
}
