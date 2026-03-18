package com.fixforge.engine.agent;

import java.io.File;
import java.util.List;

public class AgentContext {

    private final String repoUrl;
    private final File repoDir;
    private final List<File> javaFiles;

    private int attempt;
    private String currentModel;

    public AgentContext(String repoUrl, File repoDir, List<File> javaFiles, String model) {
        this.repoUrl = repoUrl;
        this.repoDir = repoDir;
        this.javaFiles = javaFiles;
        this.currentModel = model;
        this.attempt = 0;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public File getRepoDir() {
        return repoDir;
    }

    public List<File> getJavaFiles() {
        return javaFiles;
    }

    public int getAttempt() {
        return attempt;
    }

    public void incrementAttempt() {
        this.attempt++;
    }

    public boolean shouldStop() {
        return attempt >= 3;
    }

    public String getCurrentModel() {
        return currentModel;
    }

    public void switchModel() {
        if ("ollama".equalsIgnoreCase(currentModel)) {
            currentModel = "gemini";
        }
    }
}