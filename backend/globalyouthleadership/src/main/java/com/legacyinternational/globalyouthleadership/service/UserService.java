package com.legacyinternational.globalyouthleadership.service;

import com.legacyinternational.globalyouthleadership.adapter.auth.RegisterRequest;
import com.legacyinternational.globalyouthleadership.service.user.Role;
import com.legacyinternational.globalyouthleadership.service.user.User;

import java.util.List;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);
    List<User> getAllUsers();
    List<User> getUsersByRole(Role role);
    User verifyUser(String email);
    User promoteToAdmin(String email);
    User demoteToUser(String email);
    User resetPasswordToDefault(String email);
    User resetPassword(String email, String currentPassword, String newPassword);
    User requestPasswordReset(String email);
}
