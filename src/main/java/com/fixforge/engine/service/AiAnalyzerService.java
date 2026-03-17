package com.fixforge.engine.service;

import com.fixforge.engine.configutil.Prompt;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiAnalyzerService {

    @Autowired
    private ChatClient.Builder chatClientBuilder;
    @Autowired
    private GeminiService geminiService;

    public String analyzeLog(String logContent, String provider) {

        if ("gemini".equalsIgnoreCase(provider)) {
            return geminiService.analyzeLog(logContent);
        }

        return analyzeWithOllama(logContent);
    }

    private String analyzeWithOllama(String logContent) {

        ChatClient chatClient = chatClientBuilder.build();
        String prompt =  Prompt.prompt_analyzeWithOllama + logContent;

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return response.replace("```json", "")
                .replace("```", "")
                .trim();
    }
}