package com.legacyinternational.globalyouthleadership.service.user;

import com.legacyinternational.globalyouthleadership.adapter.auth.RegisterRequest;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.UserRepository;
import com.legacyinternational.globalyouthleadership.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
                    , Role.ADMIN);
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


}
