package com.fixforge.engine.service;


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

    private String cleanResponse(String text) {

        return text
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }

    private String cleanPatch(String patch) {

        if (patch == null) return "";

        String cleaned = patch
                .replace("```diff", "")
                .replace("```java", "")
                .replace("```", "")
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .trim();


        int start = cleaned.indexOf("---");
        if (start == -1) return "";

        cleaned = cleaned.substring(start);


        StringBuilder finalPatch = new StringBuilder();

        for (String line : cleaned.split("\n")) {

            if (line.startsWith("---") ||
                    line.startsWith("+++") ||
                    line.startsWith("@@") ||
                    line.startsWith("+") ||
                    line.startsWith("-") ||
                    line.startsWith(" ")) {

                finalPatch.append(line).append("\n");
            }
        }

        return finalPatch.toString().trim();
    }


}