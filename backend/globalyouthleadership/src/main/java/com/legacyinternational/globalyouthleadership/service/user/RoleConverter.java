package com.legacyinternational.globalyouthleadership.service.user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return Role.PENDING_REVIEW.getRoleName();
        }
        return role.getRoleName();
    }

    @Override
    public Role convertToEntityAttribute(String roleValue) {
        return Role.fromString(roleValue);
    }
}