package com.fixforge.engine.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeFixService {

    public void applyFix(File file, int lineNumber, String fixedCode) throws Exception {

        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File not found");
        }

        if (lineNumber <= 0) {
            throw new IllegalArgumentException("Invalid line number");
        }

        List<String> lines = Files.readAllLines(file.toPath());

        if (lineNumber > lines.size()) {
            throw new IllegalArgumentException("Line number exceeds file length");
        }

        // Clean AI output
        String cleaned = cleanCode(fixedCode);

        // 🚨 Validate AI output
        if (!isValidFix(cleaned)) {
            System.out.println("Skipping invalid AI fix: " + cleaned);
            return;
        }

        String originalLine = lines.get(lineNumber - 1);

        List<String> newLines;

        // 🚨 Handle try-catch wrapping
        if (isTryCatchFix(cleaned)) {
            newLines = wrapWithTryCatch(originalLine);
        } else {
            newLines = splitLines(cleaned);
        }

        // Replace original line
        lines.remove(lineNumber - 1);
        lines.addAll(lineNumber - 1, newLines);

        Files.write(file.toPath(), lines);

        System.out.println("Applied fix at line " + lineNumber + " in " + file.getName());
    }

    // ===============================
    // CLEAN AI RESPONSE
    // ===============================
    private String cleanCode(String code) {

        if (code == null) return "";

        return code
                .replace("```java", "")
                .replace("```", "")
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .trim();
    }

    // ===============================
    // VALIDATION
    // ===============================
    private boolean isValidFix(String code) {

        if (code == null || code.isEmpty()) return false;

        // Reject garbage like "try"
        if (code.equals("try") || code.length() < 5) return false;

        // Must contain some Java structure
        return code.contains(";") || code.contains("{");
    }

    // ===============================
    // TRY-CATCH DETECTION
    // ===============================
    private boolean isTryCatchFix(String code) {
        return code.contains("try") && code.contains("catch");
    }

    // ===============================
    // WRAP ORIGINAL LINE
    // ===============================
    private List<String> wrapWithTryCatch(String originalLine) {

        List<String> block = new ArrayList<>();

        block.add("try {");
        block.add("    " + originalLine.trim());
        block.add("} catch (Exception e) {");
        block.add("    e.printStackTrace();");
        block.add("}");

        return block;
    }

    // ===============================
    // SPLIT MULTI-LINE CODE
    // ===============================
    private List<String> splitLines(String code) {

        List<String> result = new ArrayList<>();

        String[] parts = code.split("\n");

        for (String part : parts) {
            result.add(part);
        }

        return result;
    }
}