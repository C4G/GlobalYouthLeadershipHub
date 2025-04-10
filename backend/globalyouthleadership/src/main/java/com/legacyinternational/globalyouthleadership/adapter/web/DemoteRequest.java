package com.legacyinternational.globalyouthleadership.adapter.web;

import lombok.Generated;

public class DemoteRequest {
    private String email;

    @Generated
    public static DemoteRequestBuilder builder() {
        return new DemoteRequestBuilder();
    }

    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public DemoteRequest(final String email) {
        this.email = email;
    }

    @Generated
    public void setEmail(final String email) {
        this.email = email;
    }

    @Generated
    public static class DemoteRequestBuilder {
        @Generated
        private String email;

        @Generated
        DemoteRequestBuilder() {
        }

        @Generated
        public DemoteRequestBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @Generated
        public DemoteRequest build() {
            return new DemoteRequest(this.email);
        }

        @Generated
        public String toString() {
            return "DemoteRequest.DemoteRequestBuilder(email=" + this.email + ")";
        }
    }
}
