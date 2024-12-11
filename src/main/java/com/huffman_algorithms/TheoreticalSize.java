package com.huffman_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.huffman_algorithms.AdaptiveHuffmanAlgorithm.*;
import com.huffman_algorithms.StaticHuffmanAlgorithm.StaticHuffmanEncodingStream;

public class TheoreticalSize {
    public static void main(String[] args) {

        String filePath = "D:\\JavaProjects\\huffman\\FilesForTest\\";

        int iterations = 100;
        int gap = 1000;
        List<String> fileNames = new ArrayList<>();

        createFilesAndUpdateList(fileNames, iterations, gap, filePath);
        compareTheoryAndPractice(fileNames, filePath);
    }

    public static void createFilesAndUpdateList(List<String> fileNames, int iterations, int gap, String filePath) {
        for (int i = 1; i <= iterations; i++) {
            CreateFiles.createFileWithContent(String.valueOf(i * gap), i * gap, filePath);
            fileNames.add(String.valueOf(i * gap));
        }
    }

    public static void compareTheoryAndPractice(List<String> fileNames, String filePath) {
        String csvFilePath = filePath + "comparison_results.csv";

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFilePath), StandardCharsets.UTF_8)) {
            writer.write("FileName,CompressedSize,CompressedSizeStatic,TheoreticalSize\n");

            for (String name : fileNames) {
                try {
                    String text = EntropyText.readFile(filePath + name);
                    double entropy = EntropyText.calculateEntropy(text);

                    TestMain.fileCodecDigitalTest(filePath + name, filePath + name + ".huf", filePath + name + "decoded");
                    StaticHuffmanEncodingStream.HuffmanEncoding(filePath + name, filePath + "static" +name + ".huf", filePath + "static" + name + "decoded", "UTF-8");

                    File compressedFile = new File(filePath + name + ".huf");
                    File compressedFileStatic = new File(filePath + "static" + name + ".huf");
                    long compressedSizeStatic = compressedFileStatic.length();
                    long compressedSize = compressedFile.length();
                    long theoreticalSize = (long) ((entropy * text.length()) / 8);

                    writer.write(name + "," + compressedSize + "," + compressedSizeStatic + "," + theoreticalSize + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
