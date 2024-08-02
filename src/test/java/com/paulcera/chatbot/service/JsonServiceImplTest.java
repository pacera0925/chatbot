package com.paulcera.chatbot.service;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JsonServiceImplTest {

    private JsonServiceImpl jsonService;

    @BeforeEach
    void setUp() {
        jsonService = new JsonServiceImpl();
    }

    @Test
    void createRequestBody_hasModelAndMessage_returnsValidJsonObjectRequestBody() throws JSONException {
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "context"));
        JSONObject requestBody = jsonService.createRequestBody("test-model", messages);

        assertEquals("test-model", requestBody.get("model"));
        assertEquals(messages.toString(), requestBody.get("messages").toString());
    }

    @Test
    void createMessagesParam_hasContextAndMessage_returnsValidJsonArray() throws JSONException {
        JSONArray messages = jsonService.createMessagesParam("context", "userMessage");

        assertEquals(2, messages.length());
        assertEquals("system", messages.getJSONObject(0).get("role"));
        assertEquals("context", messages.getJSONObject(0).get("content"));
        assertEquals("user", messages.getJSONObject(1).get("role"));
        assertEquals("userMessage", messages.getJSONObject(1).get("content"));
    }

    @Test
    void parseResponseBody_validResponse_returnsExpectedParsedContent() {
        String responseBody = "{ \"choices\": [ { \"message\": { \"content\": \"  parsed content  \" } } ] }";
        String result = jsonService.parseResponseBody(responseBody);

        assertEquals("parsed content", result);
    }
}