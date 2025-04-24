package com.legacyinternational.globalyouthleadership.adapter.web;

import lombok.Generated;

public class PasswordResetRequest {
    private String email;

    @Generated
    public static PasswordResetRequestBuilder builder() {
        return new PasswordResetRequestBuilder();
    }

    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public void setEmail(final String email) {
        this.email = email;
    }

    @Generated
    public PasswordResetRequest(final String email) {
        this.email = email;
    }

    @Generated
    public PasswordResetRequest() {
    }

    @Generated
    public static class PasswordResetRequestBuilder {
        private String email;

        @Generated
        PasswordResetRequestBuilder() {
        }

        @Generated
        public PasswordResetRequestBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @Generated
        public PasswordResetRequest build() {
            return new PasswordResetRequest(this.email);
        }

        @Generated
        public String toString() {
            return "PasswordResetRequest.PasswordResetRequestBuilder(email=" + this.email + ")";
        }
    }
}
