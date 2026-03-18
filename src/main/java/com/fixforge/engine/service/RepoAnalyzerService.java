package com.fixforge.engine.service;

import com.fixforge.engine.agent.FixForgeAgent;
import com.fixforge.engine.model.RepoAnalysisResponse;
import org.springframework.stereotype.Service;

@Service
public class RepoAnalyzerService {

    private final FixForgeAgent agent;

    public RepoAnalyzerService(FixForgeAgent agent) {
        this.agent = agent;
    }

    public RepoAnalysisResponse analyzeAndFixRepo(String repoUrl, String provider) throws Exception {
        return agent.run(repoUrl, provider);
    }
}