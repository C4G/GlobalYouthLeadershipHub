package com.legacyinternational.globalyouthleadership.adapter.web;

import lombok.Generated;

public class PasswordResetByUserRequest {
    private String currentPassword;
    private String newPassword;

    @Generated
    public static PasswordResetByUserRequestBuilder builder() {
        return new PasswordResetByUserRequestBuilder();
    }

    @Generated
    public String getCurrentPassword() {
        return this.currentPassword;
    }

    @Generated
    public String getNewPassword() {
        return this.newPassword;
    }

    @Generated
    public PasswordResetByUserRequest(final String currentPassword, final String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    @Generated
    public void setCurrentPassword(final String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @Generated
    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    @Generated
    public PasswordResetByUserRequest() {
    }

    @Generated
    public static class PasswordResetByUserRequestBuilder {
        private String currentPassword;
        private String newPassword;

        @Generated
        PasswordResetByUserRequestBuilder() {
        }

        @Generated
        public PasswordResetByUserRequestBuilder currentPassword(final String currentPassword) {
            this.currentPassword = currentPassword;
            return this;
        }

        @Generated
        public PasswordResetByUserRequestBuilder newPassword(final String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        @Generated
        public PasswordResetByUserRequest build() {
            return new PasswordResetByUserRequest(this.currentPassword, this.newPassword);
        }

        @Generated
        public String toString() {
            return "PasswordResetByUserRequest.PasswordResetByUserRequestBuilder(currentPassword=" + this.currentPassword + ", newPassword=" + this.newPassword + ")";
        }
    }
}
