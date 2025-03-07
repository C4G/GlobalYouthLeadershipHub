package com.legacyinternational.globalyouthleadership.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legacyinternational.globalyouthleadership.adapter.auth.LoginRequest;
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
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
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
        assertThat(response.getBody().toString()).isEqualTo("[]");
    }

    @Test
    void testGetAdminRoleUsers() {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/users/admin", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().toString()).isEqualTo("[{\"id\":1,\"email\":\"admin@gmail.com\",\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"role\":\"admin\"}]");
    }
}
