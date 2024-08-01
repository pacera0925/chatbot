package com.paulcera.chatbot.service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface JsonService {

    JSONObject createRequestBody(String model, JSONArray messages);

    JSONArray createMessagesParam(String aiChatContext, String userMessage);

    String parseResponseBody(String responseBody);

}
