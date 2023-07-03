package com.productautofillservice;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ChatGPTHelper {
    OpenAiService service;

    public ChatGPTHelper() throws IOException {
        Properties props = new Properties();
        InputStream input = new FileInputStream("product-service/src/main/resources/env.properties");
        props.load(input);
        String token = props.getProperty("CHAT_GPT_TOKEN");
        service = new OpenAiService(token, Duration.ofSeconds(60));
    }

    public String getBookExample() {
        String question;

        try {
            File file = new File("product-service/src/main/resources/chatGPT/book_question.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder contentBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                contentBuilder.append(line);
                contentBuilder.append(System.lineSeparator());
            }

            scanner.close();
            question = contentBuilder.toString();
            System.out.println(question);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return "File not found";
        }

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
