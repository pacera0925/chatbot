package com.paulcera.chatbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.paulcera.chatbot.dto.UserInput;
import com.paulcera.chatbot.exception.ChatServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private OpenAIService openAIService;

    @InjectMocks
    private ChatService chatService;

    @Test
    void processChatInput_hasProcessingError_throwsError() {
        UserInput userInput = mock(UserInput.class);
        when(openAIService.generateResponse(userInput)).thenThrow(new RestClientException("Service down"));

        ChatServiceException thrown = assertThrows(ChatServiceException.class, () -> {
            chatService.processChatInput(userInput);
        });

        assertEquals("Sorry, looks like chat service is down at the moment. I'll try to fix this as soon as I can!",
            thrown.getMessage());
        verify(openAIService).generateResponse(userInput);
    }

    @Test
    void processChatInput_noProcessingError_success() {
        UserInput userInput = mock(UserInput.class);
        String expectedResponse = "Hi there!";
        when(openAIService.generateResponse(userInput)).thenReturn(expectedResponse);

        String actualResponse = chatService.processChatInput(userInput);

        assertEquals(expectedResponse, actualResponse);
        verify(openAIService).generateResponse(userInput);
    }
}