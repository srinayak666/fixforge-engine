package com.fixforge.engine.service;


import com.fixforge.engine.configutil.Prompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OllamaCodeAnalyzerService {

    private ChatClient chatClient;


    public OllamaCodeAnalyzerService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String analyzeCode(String code) {

        String prompt = buildPrompt(code);

        try {

            String raw = chatClient
                    .prompt()
                    .user(prompt)
                    .call()
                    .content();

            return cleanFullCode(raw);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String buildPrompt(String code) {

        return Prompt.oolamaCodeAnalyzerPrompt+code;
    }

    private String cleanFullCode(String code) {

        if (code == null) return "";

        return code
                .replace("```java", "")
                .replace("```", "")
                .trim();
    }

}