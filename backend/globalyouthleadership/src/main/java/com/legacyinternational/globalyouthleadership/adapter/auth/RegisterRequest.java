package com.legacyinternational.globalyouthleadership.adapter.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDateTime dateOfBirth;

    public static void validateInput(RegisterRequest registerRequest) {
        if (Objects.isNull(registerRequest.getEmail()) || !EmailValidator.getInstance().isValid(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email is required and must be a valid email address");
        }
        if (Objects.isNull(registerRequest.getFirstName()) || registerRequest.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required and must not be empty");
        }
        if (Objects.isNull(registerRequest.getLastName()) || registerRequest.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required and must not be empty");
        }
        if (Objects.isNull(registerRequest.getDateOfBirth())) {
            throw new IllegalArgumentException("Date of birth is required and must not be empty");
        }
        if (Objects.isNull(registerRequest.getPassword()) || registerRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required and must not be empty");
        }

    }
}
