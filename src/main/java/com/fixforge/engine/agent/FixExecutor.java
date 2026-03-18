package com.fixforge.engine.agent;



import com.fixforge.engine.service.CodeAnalyzerService;
import com.fixforge.engine.service.OllamaCodeAnalyzerService;
import org.springframework.stereotype.Component;

@Component
public class FixExecutor {

    private final CodeAnalyzerService geminiService;
    private final OllamaCodeAnalyzerService ollamaService;

    public FixExecutor(CodeAnalyzerService geminiService,
                       OllamaCodeAnalyzerService ollamaService) {
        this.geminiService = geminiService;
        this.ollamaService = ollamaService;
    }

    public String executeFix(String code, String model) throws Exception {

        String response;

        if ("ollama".equalsIgnoreCase(model)) {
            response = ollamaService.analyzeCode(code);
        } else {
            response = geminiService.analyzeCode(code);
            Thread.sleep(2000);
        }

        return AgentUtils.cleanCode(response);
    }
}
