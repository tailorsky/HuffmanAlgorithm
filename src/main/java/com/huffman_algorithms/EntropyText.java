package com.huffman_algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EntropyText {
    public static void main(String[] args) {
        String filePath = "input.txt";

        try {
            String text = readFile(filePath);
            double entropy = calculateEntropy(text);
            System.out.printf("Энтропия текста из файла '%s': %.4f бит\n", filePath, entropy);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
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

    public static double calculateEntropy(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        int totalChars = text.length();
        System.out.println(totalChars);

        double entropy = 0.0;
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            double probability = (double) entry.getValue() / totalChars;
            entropy -= probability * (Math.log(probability) / Math.log(2)); // Используем log2
        }

        return entropy;
    }
}
