package com.paulcera.chatbot.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "auth.openai.api-key=test-key",
    "openai.model=test-model",
    "openai.url=test-url",
    "openai.context=test-context"
})
class ConfigurationServiceTest {

    @Autowired
    private ConfigurationService configurationService;

    @Test
    void testConfigurationService_successfullyRetrievePropertySource() {
        assertNotNull(configurationService);
        assertEquals("test-key", configurationService.getKey());
        assertEquals("test-model", configurationService.getModel());
        assertEquals("test-url", configurationService.getUrl());
        assertEquals("test-context", configurationService.getContext());
    }
}