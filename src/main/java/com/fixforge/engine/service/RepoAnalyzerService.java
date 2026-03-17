package com.fixforge.engine.service;

import com.fixforge.engine.configutil.JavaFileScanner;
import com.fixforge.engine.model.RepoAnalysisResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepoAnalyzerService {

    private  GitRepoService gitRepoService;
    private JavaFileScanner scanner;
    private  CodeAnalyzerService geminiService;
    private  OllamaCodeAnalyzerService ollamaService;
    private  GitService gitService;
    private  GitHubService githubService;

    public RepoAnalyzerService(
            GitRepoService gitRepoService,
            JavaFileScanner scanner,
            CodeAnalyzerService geminiService,
            OllamaCodeAnalyzerService ollamaService,
            GitService gitService,
            GitHubService githubService) {

        this.gitRepoService = gitRepoService;
        this.scanner = scanner;
        this.geminiService = geminiService;
        this.ollamaService = ollamaService;
        this.gitService = gitService;
        this.githubService = githubService;
    }

    public RepoAnalysisResponse analyzeAndFixRepo(String repoUrl, String provider) throws Exception {

        File repo = gitRepoService.cloneRepo(repoUrl);

        List<File> javaFiles = scanner.findJavaFiles(repo);

        List<String> fixes = new ArrayList<>();

        String branch = "ai-fix-" + System.currentTimeMillis();

        for (File file : javaFiles) {

            System.out.println("Processing file: " + file.getName());

            String originalCode = Files.readString(file.toPath());

            String fixedCode;

            // =========================
            // ROUTING
            // =========================
            if ("ollama".equalsIgnoreCase(provider)) {

                fixedCode = ollamaService.analyzeCode(originalCode);

                // 🔁 fallback to Gemini if bad
                if (!isValidJavaFile(fixedCode)) {

                    System.out.println("⚠️ Ollama failed → fallback to Gemini");
continue;
                    //fixedCode = geminiService.analyzeCode(originalCode);
                }

            } else if ("gemini".equalsIgnoreCase(provider)) {

                fixedCode = geminiService.analyzeCode(originalCode);

                Thread.sleep(4000); // avoid rate limit

            } else {
                throw new IllegalArgumentException("Invalid provider: " + provider);
            }

            // =========================
            // CLEAN RESPONSE
            // =========================
            fixedCode = cleanFullCode(fixedCode);

            System.out.println("Fixed code preview:\n" +
                    fixedCode.substring(0, Math.min(300, fixedCode.length()))
            );

            // =========================
            // VALIDATION
            // =========================
            if (!isValidJavaFile(fixedCode)) {

                System.out.println("❌ Invalid fixed file → skipping: " + file.getName());
                continue;
            }

            // =========================
            // WRITE BACK FILE
            // =========================
            try {

                Files.writeString(file.toPath(), fixedCode);

                fixes.add("Fixed file: " + file.getName());

            } catch (Exception e) {

                System.out.println("❌ Failed writing file: " + file.getName());
            }
        }

        // =========================
        // GIT OPERATIONS
        // =========================
        gitService.commitAndPush(repo, branch);

        String prUrl = githubService.createPR(repoUrl, branch);

        return new RepoAnalysisResponse(prUrl, fixes, provider);
    }

    // =========================
    // CLEAN AI RESPONSE
    // =========================
    private String cleanFullCode(String code) {

        if (code == null) return "";

        return code
                .replace("```java", "")
                .replace("```", "")
                .trim();
    }

    // =========================
    // VALIDATE JAVA FILE
    // =========================
    private boolean isValidJavaFile(String code) {

        return code != null &&
                code.contains("class") &&
                code.contains("{") &&
                code.contains("}") &&
                code.contains("public") &&
                !code.contains("Improved") &&
                !code.contains("Enhanced") &&
                !code.contains("Here is");
    }
}