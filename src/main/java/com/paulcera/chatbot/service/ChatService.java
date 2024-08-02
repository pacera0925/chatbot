package com.paulcera.chatbot.service;

import com.paulcera.chatbot.dto.UserInput;
import com.paulcera.chatbot.exception.ChatServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final OpenAIService openAIService;

    @Autowired
    public ChatService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public String processChatInput(UserInput userInput) {
        try {
            logger.info("Processing chat input: {}", userInput.getMessage());
            return openAIService.generateResponse(userInput);
        } catch (RestClientException e) {
            logger.error("Error occurred while processing chat input with message: {}. Exception: {}",
                userInput.getMessage(), e.getMessage(), e);
            throw new ChatServiceException(
                "Sorry, looks like chat service is down at the moment. I'll try to fix this as soon as I can!");
        }
    }
}
