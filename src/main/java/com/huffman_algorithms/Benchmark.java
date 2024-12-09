package com.huffman_algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.huffman_algorithms.AdaptiveHuffmanAlgorithm.*;
import com.huffman_algorithms.StaticHuffmanAlgorithm.StaticHuffmanEncodingStream;
import java.util.List;

public class Benchmark {
    public static void main(String[] args) {

        String filePath = "D:\\JavaProjects\\huffman\\FilesForTest\\";

        int iterations = 100;
        int gap = 1000;
        List<String> fileNames = new ArrayList<>();

        String algorithmType = "adaptive";

        createFilesAndUpdateList(fileNames, iterations, gap, filePath);
        testAlgorithms(fileNames, filePath, algorithmType);
    }

    public static void createFilesAndUpdateList(List<String> fileNames, int iterations, int gap, String filePath){
        for (int i = 1; i <= iterations; i++){
            CreateFiles.createFileWithContent(String.valueOf(i*gap), i*gap, filePath);
            fileNames.add(String.valueOf(i*gap));
        }
    }

    public static void testAlgorithms(List<String> fileNames, String filePath, String algorithmType){
        long[] times = new long[fileNames.size()];
        int iteration = 0;

        for (String name : fileNames){
            if (algorithmType.equals("adaptive")){
                long start = System.nanoTime();
                TestMain.fileCodecDigitalTest(filePath + name, filePath + name + ".huf", filePath + name + "decoded");
                long end = System.nanoTime();
                times[iteration] = end - start;
                iteration ++;
            }
            else if (algorithmType.equals("static")){
                long start = System.nanoTime();
                StaticHuffmanEncodingStream.HuffmanEncoding(filePath + name, filePath + name + ".huf", filePath + name + "decoded", "UTF-8");
                long end = System.nanoTime();
                times[iteration] = end - start;
                iteration ++;
            }
        }

        try(FileWriter detailedCsv = new FileWriter(algorithmType + "_" + "benchmark_results.csv")){
            detailedCsv.append("FileName,Time_ns_,Original_Size_bytes_,Compressed_Size_bytes_\n");

            int i = 0;

            for (String name : fileNames){
                File inputFile = new File(filePath + name);
                File compressedFile = new File(filePath + name + ".huf");

                detailedCsv.append(name + ",");
                detailedCsv.append(String.valueOf(times[i]) + ",");
                detailedCsv.append(String.valueOf(inputFile.length()) + ",");
                detailedCsv.append(String.valueOf(compressedFile.length()) + "\n");

                i++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
