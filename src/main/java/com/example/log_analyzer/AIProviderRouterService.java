package com.example.log_analyzer;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIProviderRouterService {

    @Autowired private  RepoAnalyzerService repoAnalyzerService;

    public AIProviderRouterService(RepoAnalyzerService repoAnalyzerService) {
        this.repoAnalyzerService = repoAnalyzerService;
    }

    public RepoAnalysisResponse analyzeRepo(String repoUrl, String provider) throws Exception {

        if (provider == null || provider.isEmpty()) {
            provider = "ollama"; // default
        }

        System.out.println("Routing to provider: " + provider);

        return repoAnalyzerService.analyzeAndFixRepo(repoUrl, provider);
    }
}