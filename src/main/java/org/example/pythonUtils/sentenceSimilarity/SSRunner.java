package org.example.pythonUtils.sentenceSimilarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SSRunner {
    public static int runScript() {

        int exitCode = 1;
        try {
            ProcessBuilder pb = new ProcessBuilder("python", new File("").getAbsolutePath() + "\\src\\main\\java\\org\\example\\pythonUtils\\sentenceSimilarity\\senSim.py");
            pb.directory(new File("src/main/java/org/example/pythonUtils/sentenceSimilarity"));
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            exitCode = p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return exitCode;
    }


    public static void main(String[] args) {
        runScript();
    }
}
