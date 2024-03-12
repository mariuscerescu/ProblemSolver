package org.example.classification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleAdditionClassifier extends Classifier {

    private String problem;

    public SimpleAdditionClassifier(String problem){
        this.problem = problem;
    }

    @Override
    public String classifyProblem() {
        // Pasul 2: Preprocesare
        String processedProblem = problem.toLowerCase().replaceAll("[^a-zăîâșț0-9\\s]", "");

        // Pasul 3: Căutarea cuvintelor cheie și Pasul 4: Extragerea și analiza numerelor
        if (containsKeywords(processedProblem) && countNumbers(processedProblem) >= 2) {
            return "simpleAddition";
        } else {
            return null;
        }
    }

    // Metoda pentru Pasul 3: Verifică dacă textul conține cuvinte cheie asociate cu adunarea
    public boolean containsKeywords(String text) {
        String[] keywords = {"transferă", "aduc", "cumpărat", "adaugi", "primește", "adaugă", "adună", "plus", "împreună", "total", "sumează", "primești", "adunat"};
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
        String problem = "Maria are 5 mere si primește încă 3 de la Ana. Câte mere are Maria acum?";

        ClassifierManager manager = new ClassifierManager(problem);

        System.out.println(manager.findClassification());
    }
}
