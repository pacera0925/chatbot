package com.paulcera.chatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final String key;
    private final String model;
    private final String url;
    private final String context;

    public ConfigurationService(@Value("${auth.openai.api-key}") String key, @Value("${openai.model}") String model,
        @Value("${openai.url}") String url, @Value("${openai.context}") String context) {
        this.key = key;
        this.model = model;
        this.url = url;
        this.context = context;
    }

    public String getKey() {
        return key;
    }

    public String getModel() {
        return model;
    }

    public String getUrl() {
        return url;
    }

    public String getContext() {
        return context;
    }

}
