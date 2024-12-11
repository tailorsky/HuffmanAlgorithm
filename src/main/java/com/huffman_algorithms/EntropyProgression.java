package com.huffman_algorithms;

import java.io.*;
import java.util.*;

public class EntropyProgression {
    public static void main(String[] args) {
        String filePath = "input.txt";
        String outputCsvPath = "entropy_progression.csv";

        try {
            String text = readFile(filePath);
            calculateEntropyProgression(text, outputCsvPath);
            System.out.println("Расчёт энтропии завершён. Данные сохранены в файл: " + outputCsvPath);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении или записи файла: " + e.getMessage());
        }
    }

    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void calculateEntropyProgression(String text, String outputCsvPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvPath))) {
            writer.write("Lenght,Entropy\n");

            Map<Character, Integer> frequencyMap = new HashMap<>();
            double entropy = 0.0;
            int totalChars = 0;

            for (int i = 0; i < text.length(); i++) {
                char currentChar = text.charAt(i);
                totalChars++;
                int currentFrequency = frequencyMap.getOrDefault(currentChar, 0);
                frequencyMap.put(currentChar, currentFrequency + 1);

                entropy = 0.0;
                for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
                    double probability = (double) entry.getValue() / totalChars;
                    entropy -= probability * (Math.log(probability) / Math.log(2));
                }

                writer.write(String.format(Locale.US, "%d,%.6f\n", totalChars, entropy));
            }
        }
    }
}
