package com.fixforge.engine.service;


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

        return """
                You are an expert Java developer.

                STRICT RULES:

                1. Return COMPLETE corrected Java file
                2. DO NOT remove class or structure
                3. DO NOT create duplicate classes
                4. DO NOT add explanation
                5. DO NOT add comments like "Improved code"
                6. Output MUST compile
                7. Only fix actual issues

                Fix:
                - Null checks
                - Divide by zero
                - Resource leaks
                - Syntax issues
                - Hardcoded credentials (replace with env variables)

                VERY IMPORTANT:
                - Keep original class name
                - Keep structure intact
                - Modify ONLY required lines

                RETURN ONLY JAVA CODE

                CODE:
                """ + code;
    }

    private String cleanFullCode(String code) {

        if (code == null) return "";

        return code
                .replace("```java", "")
                .replace("```", "")
                .trim();
    }

}