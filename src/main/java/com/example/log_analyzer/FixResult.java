package com.example.log_analyzer;



public class FixResult {

    private int line;
    private String issue;
    private String fixedCode;

    public FixResult(int line, String issue, String fixedCode) {
        this.line = line;
        this.issue = issue;
        this.fixedCode = fixedCode;
    }

    public int getLine() {
        return line;
    }

    public String getIssue() {
        return issue;
    }

    public String getFixedCode() {
        return fixedCode;
    }
}
