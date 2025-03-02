package com.legacyinternational.globalyouthleadership.adapter.auth;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private Boolean isVerified;
    private String token;
}
