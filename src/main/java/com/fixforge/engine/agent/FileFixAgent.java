package com.fixforge.engine.agent;



import org.springframework.stereotype.Component;

@Component
public class FileFixAgent {

    private final FixExecutor executor;

    public FileFixAgent(FixExecutor executor) {
        this.executor = executor;
    }

    public String processFile(String originalCode, AgentContext context) throws Exception {

        String currentCode = originalCode;

        while (!context.shouldStop()) {

            String fixedCode = executor.executeFix(currentCode, context.getCurrentModel());

            if (AgentUtils.isValidJava(fixedCode)) {
                return fixedCode;
            }

            context.incrementAttempt();
            context.switchModel();
            currentCode = fixedCode;
        }

        return originalCode;
    }
}
