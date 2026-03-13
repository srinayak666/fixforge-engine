package com.example.log_analyzer;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiAnalyzerService {

    @Autowired private  ChatClient.Builder chatClientBuilder;
    @Autowired  private  GeminiService geminiService;

    public String analyzeLog(String logContent, String provider) {

        if ("gemini".equalsIgnoreCase(provider)) {
            return geminiService.analyzeLog(logContent);
        }

        return analyzeWithOllama(logContent);
    }

    private String analyzeWithOllama(String logContent) {

        ChatClient chatClient = chatClientBuilder.build();

        String prompt = """
You are an expert production support engineer.

Analyze the following log file and return ONLY JSON.

{
 "summary":"",
 "errors":[],
 "rootCause":"",
 "recommendations":[],
 "additionalNotes":""
}

LOG CONTENT:
""" + logContent;

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return response.replace("```json", "")
                .replace("```", "")
                .trim();
    }
}