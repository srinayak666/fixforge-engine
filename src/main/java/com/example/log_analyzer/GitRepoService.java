package com.example.log_analyzer;

import org.springframework.stereotype.Service;
import org.eclipse.jgit.api.Git;
import java.io.File;

@Service
public class GitRepoService {

    public File cloneRepo(String repoUrl) throws Exception {

        File repoDir =
                new File("repos/repo-" + System.currentTimeMillis());

        Git.cloneRepository()
                .setURI(repoUrl)
                .setDirectory(repoDir)
                .call();

        return repoDir;
    }
}
