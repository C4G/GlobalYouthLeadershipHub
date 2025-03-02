package com.legacyinternational.globalyouthleadership.service.user;

public enum Role {
    PENDING_REVIEW("pending_review"),
    USER("user"),
    ADMIN("admin");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }

    public static Role fromString(String text) {
        for (Role role : Role.values()) {
            if (role.roleName.equalsIgnoreCase(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant for role: " + text);
    }
}