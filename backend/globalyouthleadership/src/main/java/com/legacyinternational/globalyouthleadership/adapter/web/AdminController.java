package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.UserResponse;
import com.legacyinternational.globalyouthleadership.service.UserService;
import com.legacyinternational.globalyouthleadership.service.user.Role;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

}
