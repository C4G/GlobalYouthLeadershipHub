package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.ApiResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.UserResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.VerifyRequest;
import com.legacyinternational.globalyouthleadership.service.UserService;
import com.legacyinternational.globalyouthleadership.service.user.Role;
import com.legacyinternational.globalyouthleadership.service.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<UserResponse> userList = allUsers.stream()
                .map(UserResponse::fromUser)
                .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/users/pending")
    public ResponseEntity<List<UserResponse>> getPendingReviewUsers() {
        List<User> pendingUsers = userService.getUsersByRole(Role.PENDING_REVIEW);
        List<UserResponse> userList = pendingUsers.stream()
                .map(UserResponse::fromUser)
                .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/users/user")
    public ResponseEntity<List<UserResponse>> getUserRoleUsers() {
        List<User> users = userService.getUsersByRole(Role.USER);
        List<UserResponse> userList = users.stream()
                .map(UserResponse::fromUser)
                .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/users/admin")
    public ResponseEntity<List<UserResponse>> getAdminRoleUsers() {
        List<User> adminUsers = userService.getUsersByRole(Role.ADMIN);
        List<UserResponse> userList = adminUsers.stream()
                .map(UserResponse::fromUser)
                .toList();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/users/verify")
    public ResponseEntity<ApiResponse> verifyUser(@RequestBody VerifyRequest verifyRequest) {

        if (Objects.isNull(verifyRequest.getEmail()) || verifyRequest.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (!EmailValidator.getInstance().isValid(verifyRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }
        userService.verifyUser(verifyRequest.getEmail());
        return ResponseEntity.ok(ApiResponse.builder()
                .message(verifyRequest.getEmail() + " has been verified and updated successfully")
                .build()
        );
    }

    @PostMapping("/users/promote-to-admin")
    public ResponseEntity<ApiResponse> promoteToAdmin(@RequestBody PromoteRequest promoteRequest) {
        if (Objects.isNull(promoteRequest.getEmail()) || promoteRequest.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (!EmailValidator.getInstance().isValid(promoteRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        User promotedUser = userService.promoteToAdmin(promoteRequest.getEmail());
        return ResponseEntity.ok(ApiResponse.builder()
                .message(promotedUser.getEmail() + " has been promoted to admin")
                .build());
    }

    @PostMapping("/users/demote-to-user")
    public ResponseEntity<ApiResponse> demoteToUser(@RequestBody DemoteRequest demoteRequest) {
        if (Objects.isNull(demoteRequest.getEmail()) || demoteRequest.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (!EmailValidator.getInstance().isValid(demoteRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        User demotedUser = userService.demoteToUser(demoteRequest.getEmail());
        return ResponseEntity.ok(ApiResponse.builder()
                .message(demotedUser.getEmail() + " has been demoted to regular user")
                .build());
    }

    @PostMapping("/reset-users-password")
    public ResponseEntity<ApiResponse> resetPasswordToDefault(
            @RequestBody PasswordResetByAdminRequest request) {

        if (!EmailValidator.getInstance().isValid(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        User updatedUser = userService.resetPasswordToDefault(request.getEmail());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message(updatedUser.getEmail() + "'s password reset to default successfully")
                        .build()
        );
    }

    @GetMapping("/users/reset-required")
    public ResponseEntity<List<UserResponse>> getUsersRequiringPasswordReset() {
        List<User> usersNeedingReset = userService.getUsersRequiringPasswordReset();
        List<UserResponse> userList = usersNeedingReset.stream()
                .map(UserResponse::fromUser)
                .toList();
        return ResponseEntity.ok(userList);
    }

}
