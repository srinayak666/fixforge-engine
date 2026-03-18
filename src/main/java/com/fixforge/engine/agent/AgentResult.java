package com.fixforge.engine.agent;



import java.util.List;

public class AgentResult {

    private final String prUrl;
    private final List<String> logs;
    private final String finalModel;

    public AgentResult(String prUrl, List<String> logs, String finalModel) {
        this.prUrl = prUrl;
        this.logs = logs;
        this.finalModel = finalModel;
    }

    public String getPrUrl() {
        return prUrl;
    }

    public List<String> getLogs() {
        return logs;
    }

    public String getFinalModel() {
        return finalModel;
    }
}