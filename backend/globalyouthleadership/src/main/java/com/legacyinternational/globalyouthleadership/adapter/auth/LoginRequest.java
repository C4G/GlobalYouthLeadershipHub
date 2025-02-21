package com.legacyinternational.globalyouthleadership.adapter.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    private String email;

    private String password;

    public static void validateLoginRequest(LoginRequest loginRequest) {
        if (Objects.isNull(loginRequest.getPassword()) || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required and must not be empty");
        }
        if (Objects.isNull(loginRequest.getEmail()) || !EmailValidator.getInstance().isValid(loginRequest.getEmail())) {
            throw new IllegalArgumentException("Email is required and must be a valid email address");
        }
    }
}
