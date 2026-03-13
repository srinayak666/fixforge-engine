package com.example.log_analyzer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LogController {

    @Autowired  private  LogService logService;
    @Autowired  private  AiAnalyzerService aiAnalyzerService;

    @PostMapping("/upload")
    public String uploadLog(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "ollama") String provider
    ) throws Exception {

        if (file.isEmpty()) {
            return """
			{
			  "summary":"File is empty",
			  "errors":["File is empty"],
			  "rootCause":"File is empty",
			  "recommendations":["Upload a valid log file"],
			  "additionalNotes":"File is empty"
			}
			""";
        }

        String filteredLogs = logService.extractImportantLogs(file);

        return aiAnalyzerService.analyzeLog(filteredLogs, provider);
    }
}