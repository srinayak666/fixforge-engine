package com.fixforge.engine.configutil;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class JavaFileScanner {

    public List<File> findJavaFiles(File repoDir) {

        List<File> files = new ArrayList<>();

        scan(repoDir, files);

        return files;
    }

    private void scan(File dir, List<File> files) {

        for (File f : dir.listFiles()) {

            if (f.isDirectory()) {
                scan(f, files);
            }

            if (f.getName().endsWith(".java")) {
                files.add(f);
            }
        }
    }
}
