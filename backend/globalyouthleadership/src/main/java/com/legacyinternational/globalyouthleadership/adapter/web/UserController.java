package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.ApiResponse;
import com.legacyinternational.globalyouthleadership.service.UserService;
import com.legacyinternational.globalyouthleadership.service.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestBody PasswordResetByUserRequest request,
            Principal principal) {

        User updatedUser = userService.resetPassword(
                principal.getName(),
                request.getCurrentPassword(),
                request.getNewPassword()
        );

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message(updatedUser.getEmail() + "'s password updated successfully")
                        .build()
        );
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<ApiResponse> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        User updatedUser = userService.requestPasswordReset(request.getEmail());

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Password reset requested for " + updatedUser.getEmail())
                        .build()
        );
    }

}
