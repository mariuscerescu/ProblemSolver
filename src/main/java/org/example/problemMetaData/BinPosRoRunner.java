package org.example.problemMetaData;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class BinPosRoRunner {

    public static ProblemMetaData runTextAnalysis(String textToAnalyze) {
        Path inputPath = Paths.get("src/main/java/org/example/problemMetaData/bin_PosRo/inputuri/intrebaria.txt");

        // Read the content of the file
        String fileContent = "";
        try {
            fileContent = Files.readString(inputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Only proceed if the textToAnalyze is different from the file content
        if (!textToAnalyze.equals(fileContent)) {
            try {
                Files.write(inputPath, textToAnalyze.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                ProcessBuilder processBuilder = new ProcessBuilder("D:\\Work\\ProblemSolver\\src\\main\\java\\org\\example\\problemMetaData\\bin_PosRo\\start tagging inputs - mixedDiacr.bat");
                processBuilder.directory(new File("src/main/java/org/example/problemMetaData/bin_PosRo"));
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();

                Scanner scanner = new Scanner(process.getInputStream());
                OutputStream outputStream = process.getOutputStream();

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
//                    System.out.println(line);

                    if (line.contains(" ")) {
                        outputStream.write("\n".getBytes());
                        outputStream.flush();
                    }
                }

                process.waitFor();
                scanner.close();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }

        return XMLDeserializer.getPOSOutput(textToAnalyze);
    }

}

