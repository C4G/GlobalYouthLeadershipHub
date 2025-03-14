package com.legacyinternational.globalyouthleadership.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legacyinternational.globalyouthleadership.adapter.auth.LoginRequest;
import com.legacyinternational.globalyouthleadership.adapter.auth.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminControllerIntegrationTest {
    private final String baseUrl = "http://localhost:";
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private final String testUserEmail = "test@example.com";

    @BeforeEach
    public void setUp() throws Exception {
        RestTemplateBuilder builder = new RestTemplateBuilder().rootUri(baseUrl + port);
        restTemplate = new TestRestTemplate(builder);
        // Given valid credentials
        String username = "admin@gmail.com";
        String password = "admin123";
        // Create request
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Perform request
        String response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn().getResponse().getContentAsString();

        token = objectMapper.readTree(response).get("token").asText();

        registerTestUser();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private void registerTestUser() {
        RegisterRequest request = new RegisterRequest(
                testUserEmail, "password123", "Test", "User", LocalDateTime.MAX
        );

        HttpEntity<String> entity = new HttpEntity<>(toJson(request), getJsonHeadersWithAuth());
        restTemplate.postForEntity("/api/auth/register", entity, String.class);
    }

    private HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders getJsonHeadersWithAuth() {
        HttpHeaders headers = getJsonHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    @Test
    void testGetAllUsers() {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/users", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().toString()).containsIgnoringCase("{\"id\":1,\"email\":\"admin@gmail.com\",\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"role\":\"admin\"}");
    }

    @Test
    void testGetPendingReviewUsers() {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/users/pending", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void testGetUserRoleUsers() {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/users/user", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void testGetAdminRoleUsers() {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/users/admin", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().toString()).isEqualTo("[{\"id\":1,\"email\":\"admin@gmail.com\",\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"role\":\"admin\"}]");
    }

    @Test
    void verifyUser_ValidRequest_ReturnsSuccess() {
        VerifyRequest request = new VerifyRequest(testUserEmail);
        HttpEntity<String> entity = new HttpEntity<>(toJson(request), getJsonHeadersWithAuth());

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                "/api/admin/users/verify", HttpMethod.POST, entity, ApiResponse.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo(testUserEmail + " has been verified and updated successfully");
    }

    @Test
    void verifyUser_NullEmail_ReturnsBadRequest() {
        VerifyRequest request = new VerifyRequest(null);
        HttpEntity<String> entity = new HttpEntity<>(toJson(request), getJsonHeadersWithAuth());

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/users/verify", HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("Email is required");
    }

    @Test
    void verifyUser_EmptyEmail_ReturnsBadRequest() {
        VerifyRequest request = new VerifyRequest("");
        HttpEntity<String> entity = new HttpEntity<>(toJson(request), getJsonHeadersWithAuth());

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/users/verify", HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("Email is required");
    }

    @Test
    void verifyUser_InvalidEmail_ReturnsBadRequest() {
        VerifyRequest request = new VerifyRequest("invalid-email");
        HttpEntity<String> entity = new HttpEntity<>(toJson(request), getJsonHeadersWithAuth());

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/users/verify", HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("Invalid email");
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }
}
