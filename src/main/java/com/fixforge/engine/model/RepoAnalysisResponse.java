package com.fixforge.engine.model;


import java.util.List;

public class RepoAnalysisResponse {

    private String pullRequestUrl;
    private List<String> fixes;
    private String providerUsed;

    public RepoAnalysisResponse() {
    }

    public RepoAnalysisResponse(String pullRequestUrl, List<String> fixes, String providerUsed) {
        this.pullRequestUrl = pullRequestUrl;
        this.fixes = fixes;
        this.providerUsed = providerUsed;
    }

    public String getPullRequestUrl() {
        return pullRequestUrl;
    }

    public void setPullRequestUrl(String pullRequestUrl) {
        this.pullRequestUrl = pullRequestUrl;
    }

    public List<String> getFixes() {
        return fixes;
    }

    public void setFixes(List<String> fixes) {
        this.fixes = fixes;
    }

    public String getProviderUsed() {
        return providerUsed;
    }

    public void setProviderUsed(String providerUsed) {
        this.providerUsed = providerUsed;
    }
}