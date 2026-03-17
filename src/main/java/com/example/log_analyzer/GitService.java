package com.example.log_analyzer;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GitService {

    @Value("${github.token}")
    private String token;

    public void commitAndPush(File repoDir,
                              String branch) throws Exception {

        Git git = Git.open(repoDir);

        git.checkout()
                .setCreateBranch(true)
                .setName(branch)
                .call();

        git.add().addFilepattern(".").call();

        git.commit()
                .setMessage("AI auto code fix")
                .call();

        git.push()
                .setCredentialsProvider(
                        new UsernamePasswordCredentialsProvider(
                                token, ""))
                .call();
    }
}
