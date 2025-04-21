package com.legacyinternational.globalyouthleadership.adapter.web;

import lombok.Generated;

public class PasswordResetByAdminRequest {
    public String email;

    @Generated
    public static PasswordResetByAdminRequestBuilder builder() {
        return new PasswordResetByAdminRequestBuilder();
    }

    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public PasswordResetByAdminRequest(final String email) {
        this.email = email;
    }

    @Generated
    public void setEmail(final String email) {
        this.email = email;
    }

    @Generated
    public PasswordResetByAdminRequest() {
    }

    @Generated
    public static class PasswordResetByAdminRequestBuilder {
        public String email;

        @Generated
        PasswordResetByAdminRequestBuilder() {
        }

        @Generated
        public PasswordResetByAdminRequestBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @Generated
        public PasswordResetByAdminRequest build() {
            return new PasswordResetByAdminRequest(this.email);
        }

        @Generated
        public String toString() {
            return "PasswordResetByAdminRequest.PasswordResetByAdminRequestBuilder(email=" + this.email + ")";
        }
    }
}
