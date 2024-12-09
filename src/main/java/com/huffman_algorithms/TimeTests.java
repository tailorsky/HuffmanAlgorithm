package com.huffman_algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.huffman_algorithms.AdaptiveHuffmanAlgorithm.*;

public class TimeTests {public static void main(String[] args) {
        int iterations = 1;

        long[] timesEncode = new long[iterations];
        long[] timesDecode = new long[iterations];

        File inputFile = new File("enwik9");
        File compressedFile = new File("enwik9.huf");
        File decompressedFile = new File("enwik9decoded");

        long inputFileSize = inputFile.length();
        long compressedFileSize = 0;
        long decompressedFileSize = 0;

        for (int i = 0; i < iterations; i++) {

            long startEncode = System.nanoTime();
            TestMain.fileEncodeTest("enwik9", "enwik9.huf");
            long endEncode = System.nanoTime();
            timesEncode[i] = endEncode - startEncode;

            long startDecode = System.nanoTime();
            TestMain.fileDecodeTest("enwik9.huf", "enwik9decoded");
            long endDecode = System.nanoTime();
            timesDecode[i] = endDecode - startDecode;
        }

        compressedFileSize = compressedFile.length();
        decompressedFileSize = decompressedFile.length();

        double avgTimeEncodeNsPerByte = Arrays.stream(timesEncode).average().orElse(0) / inputFileSize;
        double avgTimeDecodeNsPerByte = Arrays.stream(timesDecode).average().orElse(0) / compressedFileSize;

        long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("-------------------------------------------------------------");
        System.out.println("Compressed size: " + compressedFileSize + " bytes");
        System.out.println("Original size: " + inputFileSize + " bytes");
        System.out.println("Decompressed size: " + decompressedFileSize + " bytes");
        System.out.printf("Time (ns/byte):   Comp: %.2f   Decomp: %.2f\n", avgTimeEncodeNsPerByte, avgTimeDecodeNsPerByte);
        System.out.println("Memory used (MB): " + memoryUsed / (1024 * 1024));
        System.out.println("-------------------------------------------------------------");

        try {
            try (FileWriter detailedCsv = new FileWriter("detailed_results.csv")) {
                detailedCsv.append("Iteration,Original Size (bytes),Compressed Size (bytes),Decompressed Size (bytes),");
                detailedCsv.append("Encode Time (ns),Decode Time (ns),Encode Time (ns/byte),Decode Time (ns/byte),Memory Used (MB)\n");

                for (int i = 0; i < iterations; i++) {
                    double encodeTimeNsPerByte = timesEncode[i] / (double) inputFileSize;
                    double decodeTimeNsPerByte = timesDecode[i] / (double) compressedFileSize;

                    detailedCsv.append((i + 1) + ",");
                    detailedCsv.append(inputFileSize + ",");
                    detailedCsv.append(compressedFileSize + ",");
                    detailedCsv.append(decompressedFileSize + ",");
                    detailedCsv.append(timesEncode[i] + ",");
                    detailedCsv.append(timesDecode[i] + ",");
                    detailedCsv.append(String.format("%.2f", encodeTimeNsPerByte) + ",");
                    detailedCsv.append(String.format("%.2f", decodeTimeNsPerByte) + ",");
                    detailedCsv.append((memoryUsed / (1024 * 1024)) + "\n");
                }
            }

            try (FileWriter summaryCsv = new FileWriter("summary_results.csv")) {
                summaryCsv.append("Original Size (bytes),Compressed Size (bytes),Decompressed Size (bytes),");
                summaryCsv.append("Avg Encode Time (ns/byte),Avg Decode Time (ns/byte),Memory Used (MB)\n");

                summaryCsv.append(inputFileSize + ",");
                summaryCsv.append(compressedFileSize + ",");
                summaryCsv.append(decompressedFileSize + ",");
                summaryCsv.append(String.format("%.2f", avgTimeEncodeNsPerByte) + ",");
                summaryCsv.append(String.format("%.2f", avgTimeDecodeNsPerByte) + ",");
                summaryCsv.append((memoryUsed / (1024 * 1024)) + "\n");
            }

            try (FileWriter timeCsv = new FileWriter("times_only.csv")) {
                timeCsv.append("Iteration,Encode Time (ns),Decode Time (ns)\n");

                for (int i = 0; i < iterations; i++) {
                    timeCsv.append((i + 1) + ",");
                    timeCsv.append(timesEncode[i] + ",");
                    timeCsv.append(timesDecode[i] + "\n");
                }
            }

            System.out.println("Данные сохранены в файлы: detailed_results.csv, summary_results.csv, times_only.csv");
        } catch (IOException e) {
            System.err.println("Ошибка записи в файлы: " + e.getMessage());
        }
    }
}
