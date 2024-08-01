package com.paulcera.chatbot.controller;

import com.paulcera.chatbot.dto.UserInput;
import com.paulcera.chatbot.exception.ChatServiceException;
import com.paulcera.chatbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody UserInput userInput) {
        try {
            String response = chatService.processChatInput(userInput);
            return ResponseEntity.ok(response);
        } catch (ChatServiceException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
