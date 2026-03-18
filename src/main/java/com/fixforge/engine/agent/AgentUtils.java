package com.fixforge.engine.agent;



import com.github.javaparser.StaticJavaParser;

public class AgentUtils {

    public static String cleanCode(String code) {
        if (code == null) return "";
        return code.replace("```java", "")
                .replace("```", "")
                .trim();
    }

    public static boolean isValidJava(String code) {
        try {
            StaticJavaParser.parse(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
