/*
package com.example.log_analyzer;



import com.google.ai.client.GenerativeModel;
import com.google.ai.client.GenerativeModelResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleAiAnalyzerService {

    private final GenerativeModel model;

    public AiAnalyzerService(@Value("${google.ai.api.key}") String apiKey) {

        this.model = new GenerativeModel(
                "gemini-1.5-flash",
                apiKey
        );
    }

    public String analyzeLog(String logContent) {

        String prompt = """
You are an expert production support engineer.

Analyze the following log file and return ONLY JSON.

IMPORTANT RULES:
- Return ONLY valid JSON
- No markdown
- No ```json
- No explanations

JSON FORMAT:

{
  "summary": "short summary",
  "errors": ["errors found"],
  "rootCause": "root cause",
  "recommendations": ["recommended fixes"],
  "additionalNotes": "extra notes"
}

LOG CONTENT:
""" + logContent;

        GenerativeModelResponse response = model.generateContent(prompt);

        return response.text();
    }
}*/
