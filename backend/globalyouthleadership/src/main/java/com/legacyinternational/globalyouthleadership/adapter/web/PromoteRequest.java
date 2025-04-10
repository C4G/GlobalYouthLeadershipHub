package com.legacyinternational.globalyouthleadership.adapter.web;

import lombok.Generated;

public class PromoteRequest {
    private String email;

    @Generated
    public static PromoteRequestBuilder builder() {
        return new PromoteRequestBuilder();
    }

    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public PromoteRequest(final String email) {
        this.email = email;
    }

    @Generated
    public void setEmail(final String email) {
        this.email = email;
    }

    @Generated
    public static class PromoteRequestBuilder {
        @Generated
        private String email;

        @Generated
        PromoteRequestBuilder() {
        }

        @Generated
        public PromoteRequestBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @Generated
        public PromoteRequest build() {
            return new PromoteRequest(this.email);
        }

        @Generated
        public String toString() {
            return "PromoteRequest.PromoteRequestBuilder(email=" + this.email + ")";
        }
    }
}
