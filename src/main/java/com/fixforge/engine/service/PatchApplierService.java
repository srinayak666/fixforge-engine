package com.fixforge.engine.service;


import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatchApplierService {

    public void applyPatch(File file, String patch) throws Exception {

        List<String> originalLines = Files.readAllLines(file.toPath());
        List<String> newLines = new ArrayList<>();

        String[] patchLines = patch.split("\n");

        int originalIndex = 0;

        for (String line : patchLines) {


            if (line.startsWith("---") || line.startsWith("+++")
                    || line.startsWith("@@")) {
                continue;
            }


            if (line.startsWith("-")) {
                originalIndex++;
            } else if (line.startsWith("+")) {
                newLines.add(line.substring(1));
            } else {
                if (originalIndex < originalLines.size()) {
                    newLines.add(originalLines.get(originalIndex));
                    originalIndex++;
                }
            }
        }

        Files.write(file.toPath(), newLines);

        System.out.println("Patch applied to " + file.getName());
    }
}