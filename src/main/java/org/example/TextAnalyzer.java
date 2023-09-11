package org.example;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class TextAnalyzer {

    private final String xmlFilePath = "D:\\Work\\ProblemSolver\\bin_PosRo\\outputuri\\intrebaria.xml";
    public void runTextAnalysis(String textToAnalyze) {
        Path inputPath = Paths.get("D:\\Work\\ProblemSolver\\bin_PosRo\\inputuri\\intrebaria.txt");

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
                ProcessBuilder processBuilder = new ProcessBuilder("D:\\Work\\ProblemSolver\\bin_PosRo\\start tagging inputs - mixedDiacr.bat");
                processBuilder.directory(new File("D:\\Work\\ProblemSolver\\bin_PosRo"));
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
    }


    public ArrayList<String> getLemmaWords() {

        ArrayList<String> lemmaWords = new ArrayList<>();

        try {
            File inputFile = new File(xmlFilePath);

            // Crearea unei fabrici pentru document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            // Normalizează textul
            doc.getDocumentElement().normalize();

            // Ia toate nodurile W
            NodeList nList = doc.getElementsByTagName("W");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String lemma = eElement.getAttribute("LEMMA");
                    lemmaWords.add(lemma);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lemmaWords;
    }

    public ArrayList<String> getListOfPOS() {

        ArrayList<String> listOfPos = new ArrayList<>();

        try {
            File inputFile = new File(xmlFilePath);

            // Crearea unei fabrici pentru document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            // Normalizează textul
            doc.getDocumentElement().normalize();

            // Ia toate nodurile W
            NodeList nList = doc.getElementsByTagName("W");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String pos = eElement.getAttribute("POS");
                    listOfPos.add(pos);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfPos;
    }

    public ArrayList<String> getListOfTokens() {

        ArrayList<String> listOfWords = new ArrayList<>();

        try {
            File inputFile = new File(xmlFilePath);

            // Crearea unei fabrici pentru document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            // Normalizează textul
            doc.getDocumentElement().normalize();

            // Ia toate nodurile W
            NodeList nList = doc.getElementsByTagName("W");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String wordContent = eElement.getTextContent();
                    listOfWords.add(wordContent);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfWords;
    }

}

