package com.fixforge.engine.model;


public class RepoRequest {

    private String repoUrl;
    private String provider; // ollama | gemini

    public RepoRequest() {
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}