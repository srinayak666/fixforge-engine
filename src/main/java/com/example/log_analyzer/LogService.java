package com.example.log_analyzer;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

@Service
public class LogService {

    private static final Pattern IMPORTANT_PATTERN =
            Pattern.compile("ERROR|WARN|Exception|Caused by", Pattern.CASE_INSENSITIVE);

    public String extractImportantLogs(MultipartFile file) throws Exception {

        StringBuilder filteredLogs = new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean captureStackTrace = false;

            while ((line = reader.readLine()) != null) {

                if (IMPORTANT_PATTERN.matcher(line).find()) {
                    filteredLogs.append(line).append("\n");
                    captureStackTrace = true;
                    continue;
                }

                if (captureStackTrace && line.trim().startsWith("at ")) {
                    filteredLogs.append(line).append("\n");
                    continue;
                }

                captureStackTrace = false;
            }
        }

        return filteredLogs.toString();
    }
}