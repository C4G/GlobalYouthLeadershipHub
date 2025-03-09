package com.legacyinternational.globalyouthleadership.adapter.web.models;

import com.legacyinternational.globalyouthleadership.service.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserResponse {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().getRoleName())
                .build();
    }
}
