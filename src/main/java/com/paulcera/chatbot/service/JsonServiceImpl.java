package com.paulcera.chatbot.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class JsonServiceImpl implements JsonService {

    @Override
    public JSONObject createRequestBody(String model, JSONArray messages) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("messages", messages);
        return requestBody;
    }

    @Override
    public JSONArray createMessagesParam(String aiChatContext, String userMessage) {
        JSONArray messagesArray = new JSONArray();
        messagesArray.put(createMessage("system", aiChatContext));
        messagesArray.put(createMessage("user", userMessage));
        return messagesArray;
    }

    @Override
    public String parseResponseBody(String responseBody) {
        JSONObject responseJson = new JSONObject(responseBody);
        return responseJson.getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content")
            .trim();
    }

    private JSONObject createMessage(String role, String content) {
        JSONObject message = new JSONObject();
        message.put("role", role);
        message.put("content", content);
        return message;
    }

}
