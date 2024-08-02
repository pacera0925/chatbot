package com.paulcera.chatbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class HttpRequestServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HttpRequestService httpRequestService;

    @Test
    void testSendPostRequest() {
        String url = "https://example.com/api";
        HttpHeaders headers = new HttpHeaders();
        String body = "test body";
        ResponseEntity<String> mockResponse = new ResponseEntity<>("response body", HttpStatus.OK);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
            .thenReturn(mockResponse);

        ResponseEntity<String> response = httpRequestService.sendPostRequest(url, headers, body);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("response body", response.getBody());
        verify(restTemplate).exchange(eq(url), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testCreateHeaders() {
        String key = "my-secret-key";

        HttpHeaders headers = httpRequestService.createHeaders(key);

        assertNotNull(headers);
        assertEquals("Bearer my-secret-key", headers.getFirst("Authorization"));
        assertEquals("application/json", headers.getFirst("Content-Type"));
    }
}