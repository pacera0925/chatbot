package com.paulcera.chatbot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulcera.chatbot.dto.UserInput;
import com.paulcera.chatbot.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatService chatService;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("auth.openai.api-key", () -> "OPENAI_VALID_API");
        registry.add("auth.app.api-key", () -> "APP_VALID_API");
        registry.add("openai.context", () -> "AI_STRING_CONTEXT");
    }

    @Test
    @WithMockUser
    void chat_whenValidInput_thenReturns200() throws Exception {
        UserInput userInput = new UserInput();
        userInput.setMessage("Hello");

        given(chatService.processChatInput(any(UserInput.class))).willReturn("Hi there!");

        mockMvc.perform(post("/api/v1/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "APP_VALID_API")
                .content(objectMapper.writeValueAsString(userInput)))
            .andExpect(status().isOk())
            .andExpect(content().string("Hi there!"));
    }

    @Test
    @WithMockUser
    void chat_whenInvalidInput_thenReturns400() throws Exception {
        UserInput userInput = new UserInput();
        userInput.setMessage("");

        mockMvc.perform(post("/api/v1/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "APP_VALID_API")
                .content(objectMapper.writeValueAsString(userInput)))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{'message':'Message must not be empty'}"));
    }

    @Test
    @WithMockUser
    void chat_whenInvalidApiKey_thenReturns401() throws Exception {
        UserInput userInput = new UserInput();
        userInput.setMessage("Hello");

        mockMvc.perform(post("/api/v1/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "APP_INVALID_API")
                .content(objectMapper.writeValueAsString(userInput)))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Invalid API Key"));
    }
}