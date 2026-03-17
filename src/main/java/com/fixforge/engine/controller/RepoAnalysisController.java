package com.fixforge.engine.controller;


import com.fixforge.engine.model.RepoAnalysisResponse;
import com.fixforge.engine.model.RepoRequest;
import com.fixforge.engine.service.AIProviderRouterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repo")
@CrossOrigin(origins = "*")
public class RepoAnalysisController {

    private AIProviderRouterService routerService;

    public RepoAnalysisController(AIProviderRouterService routerService) {
        this.routerService = routerService;
    }

    @PostMapping("/analyze")
    public RepoAnalysisResponse analyzeRepo(@RequestBody RepoRequest request) throws Exception {

        if (request.getRepoUrl() == null || request.getRepoUrl().isEmpty()) {
            throw new IllegalArgumentException("Repository URL is required");
        }

        return routerService.analyzeRepo(
                request.getRepoUrl(),
                request.getProvider()
        );
    }
}