package com.paulcera.chatbot.dto;


import jakarta.validation.constraints.NotEmpty;

public class UserInput {

    @NotEmpty(message = "Message must not be empty")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

