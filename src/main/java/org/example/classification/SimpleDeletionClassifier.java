package org.example.classification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleDeletionClassifier implements Classifier {

    private String problem;

    public SimpleDeletionClassifier(String problem){
        this.problem = problem;
    }

    @Override
    public String classifyProblem() {
        // Pasul 2: Preprocesare
        String processedProblem = problem.toLowerCase().replaceAll("[^a-zăîâșț0-9\\s]", "");

        // Pasul 3: Căutarea cuvintelor cheie și Pasul 4: Extragerea și analiza numerelor
        if (containsKeywords(processedProblem) && countNumbers(processedProblem) >= 2) {
            return "simpleDeletion";
        } else {
            return null;
        }
    }

    // Metoda pentru Pasul 3: Verifică dacă textul conține cuvinte cheie asociate cu adunarea
    public boolean containsKeywords(String text) {
        String[] keywords = {"absenți", "mănânci", "rămâne", "mută"};
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    // Metoda pentru Pasul 4: Numără numerele din text pentru a verifica dacă problema implică adunare
    public int countNumbers(String text) {
        Matcher m = Pattern.compile("\\d+").matcher(text);
        int count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }

    public static void main(String[] args) {

        // Exemplu de problemă pentru clasificare
        String problem = "Dacă ai 15 mere și mănânci 7 dintre ele, câte mere îți rămân?";

        ClassifierManager manager = new ClassifierManager(problem);

        System.out.println(manager.findClassification());
    }
}
