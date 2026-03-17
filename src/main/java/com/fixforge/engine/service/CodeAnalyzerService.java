package com.fixforge.engine.service;


import com.fixforge.engine.configutil.Prompt;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CodeAnalyzerService {

    private final Client client;
    private final String model;

    public CodeAnalyzerService(
            @Value("${google.ai.api.key}") String apiKey,
            @Value("${gemini.model}") String model) {

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();

        this.model = model;
    }

    public String analyzeCode(String code) {

        String prompt = buildPrompt(code);

        try {

            GenerateContentResponse response =
                    client.models.generateContent(
                            model,
                            prompt,
                            GenerateContentConfig.builder().build()
                    );

            String raw = response.text();

            return cleanFullCode(raw);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String buildPrompt(String code) {

        return Prompt.geminiPromptCodeAnayzer+code;
    }

    private String cleanFullCode(String code) {

        if (code == null) return "";

        return code
                .replace("```java", "")
                .replace("```", "")
                .trim();
    }

    private String cleanResponse(String text) {

        return text
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }


}