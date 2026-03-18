package com.fixforge.engine.agent;

import com.fixforge.engine.configutil.JavaFileScanner;
import com.fixforge.engine.model.RepoAnalysisResponse;
import com.fixforge.engine.service.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class FixForgeAgent {

    private  GitRepoService gitRepoService;
    private  JavaFileScanner scanner;
    private  GitService gitService;
    private  GitHubService gitHubService;
    private  FileFixAgent fileFixAgent;

    public FixForgeAgent(GitRepoService gitRepoService,
                         JavaFileScanner scanner,
                         GitService gitService,
                         GitHubService gitHubService,
                         FileFixAgent fileFixAgent) {
        this.gitRepoService = gitRepoService;
        this.scanner = scanner;
        this.gitService = gitService;
        this.gitHubService = gitHubService;
        this.fileFixAgent = fileFixAgent;
    }

    public RepoAnalysisResponse run(String repoUrl, String model) throws Exception {

        File repoDir = gitRepoService.cloneRepo(repoUrl);
        List<File> javaFiles = scanner.findJavaFiles(repoDir);

        AgentContext context = new AgentContext(repoUrl, repoDir, javaFiles, model);

        List<String> logs = new ArrayList<>();
        String branch = "ai-fix-" + System.currentTimeMillis();

        for (File file : javaFiles) {
            System.out.println("PROCESSING FILE....."+file.getName());
            String originalCode = Files.readString(file.toPath());

            String fixedCode = fileFixAgent.processFile(originalCode, context);

            if (!fixedCode.equals(originalCode)) {
                Files.writeString(file.toPath(), fixedCode);
                logs.add("Fixed: " + file.getName());
            } else {
                logs.add("Skipped: " + file.getName());
            }
        }

        gitService.commitAndPush(repoDir, branch);
        String prUrl = gitHubService.createPR(repoUrl, branch);

        return new RepoAnalysisResponse(prUrl, logs, context.getCurrentModel());
    }
}