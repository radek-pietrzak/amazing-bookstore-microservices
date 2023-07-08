package com.productautofillservice;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ChatGPTHelper {
    OpenAiService service;

    public ChatGPTHelper() throws IOException {
        Properties props = new Properties();
        InputStream input = new FileInputStream("product-autofill-service/src/main/resources/env.properties");
        props.load(input);
        String token = props.getProperty("CHAT_GPT_TOKEN");
        service = new OpenAiService(token, Duration.ofSeconds(60));
    }

    public String getLanguage(String title) {
        String question = "Give me in answer only ISO 639-2  (3 letters) code of language of this title \"" +
                title +
                "\" and nothing more.";

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", question)))
                .model("gpt-3.5-turbo")
                .build();

        List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();
        StringBuilder stringBuilder = new StringBuilder();
        choices.stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
