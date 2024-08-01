package com.paulcera.chatbot.service;

import com.paulcera.chatbot.dto.UserInput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private final HttpRequestService httpRequestService;
    private final JsonServiceImpl jsonService;
    private final ConfigurationService configurationService;

    @Autowired
    public OpenAIService(HttpRequestService httpRequestService, JsonServiceImpl jsonService,
        ConfigurationService configurationService) {
        this.httpRequestService = httpRequestService;
        this.jsonService = jsonService;
        this.configurationService = configurationService;
    }

    public String generateResponse(UserInput userInput) {
        HttpHeaders headers = httpRequestService.createHeaders(configurationService.getKey());
        JSONArray messages = jsonService.createMessagesParam(configurationService.getContext(), userInput.getMessage());
        JSONObject requestBody = jsonService.createRequestBody(configurationService.getModel(), messages);
        ResponseEntity<String> response = httpRequestService.sendPostRequest(configurationService.getUrl(), headers,
            requestBody.toString());
        return jsonService.parseResponseBody(response.getBody());
    }

}
