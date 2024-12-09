package com.huffman_algorithms.StaticHuffmanAlgorithm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class StaticHuffmanEncodingStream {
        public static void HuffmanEncoding(String inputFilePath, String outputFilePath, String outputDecodedFilePath, String charset) {
        try {
            Map<Character, Integer> frequences = countFrequency(inputFilePath, charset);
            ArrayList<CodeTreeNode> codeTreeNodes = new ArrayList<>();
            for (Character c : frequences.keySet()) {
                codeTreeNodes.add(new CodeTreeNode(c, frequences.get(c)));
            }
            CodeTreeNode tree = huffman(codeTreeNodes);

            TreeMap<Character, String> codes = new TreeMap<>();
            for (Character c : frequences.keySet()) {
                codes.put(c, tree.getCodeForCharacter(c, ""));
            }

            try (Reader reader = new InputStreamReader(new FileInputStream(inputFilePath), charset);
                 BitToByteWriter writer = new BitToByteWriter(new FileOutputStream(outputFilePath))) {

                int charData;
                while ((charData = reader.read()) != -1) {
                    char character = (char) charData;
                    String code = codes.get(character);

                    for (char bit : code.toCharArray()) {
                        writer.writeBit(bit == '1' ? 1 : 0);
                    }
                }
                //writer.close();
                StaticHuffmanDecodingStream.decodeHuffman(outputFilePath, outputDecodedFilePath, tree);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Character, Integer> countFrequency(String filePath, String charset) throws IOException {
        Map<Character, Integer> frequencyMap = new TreeMap<>();

        try (Reader reader = new InputStreamReader(new FileInputStream(filePath), charset)) {
            int charData;
            while ((charData = reader.read()) != -1) {
                char character = (char) charData;

                frequencyMap.put(character, frequencyMap.getOrDefault(character, 0) + 1);
            }
        }

        return frequencyMap;
    }

    private static CodeTreeNode huffman(ArrayList<CodeTreeNode> codeTreeNodes){
        while (codeTreeNodes.size() > 1){
            Collections.sort(codeTreeNodes);
            CodeTreeNode left = codeTreeNodes.remove(codeTreeNodes.size() - 1);
            CodeTreeNode right = codeTreeNodes.remove(codeTreeNodes.size()-1);

            CodeTreeNode parent = new CodeTreeNode(null, right.weight + left.weight, left, right);
            codeTreeNodes.add(parent);
        }
        return codeTreeNodes.get(0);
    }

    public static void main(String[] args) {
        String inputFilePath = "D:\\JavaProjects\\huffman\\text.txt";
        String outputFilePath = "D:\\JavaProjects\\huffman\\test.huf";
        String charset = "UTF-8";

        HuffmanEncoding(inputFilePath, outputFilePath, outputFilePath, charset);
    }
}
