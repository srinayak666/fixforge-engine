package com.fixforge.engine.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;
    private final String model;

    public GeminiService(
            @Value("${google.ai.api.key}") String apiKey,
            @Value("${gemini.model}") String model
    ) {

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();

        this.model = model;
    }

    public String analyzeLog(String logContent) {

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

        GenerateContentResponse response =
                client.models.generateContent(
                        model,
                        prompt,
                        null
                );

        String text = response.text();

        if (text == null) {
            return "{}";
        }

        return text
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }
}