package com.fixforge.engine.service;

import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Service;

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
