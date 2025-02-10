package com.legacyinternational.globalyouthleadership.adapter.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebControllerIntegrationTest {
    private final String baseUrl = "http://localhost:";
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestTemplateBuilder builder = new RestTemplateBuilder().rootUri(baseUrl + port);
        restTemplate = new TestRestTemplate(builder);
    }

    @Test
    void testPing() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/ping", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("pong");
    }
}
