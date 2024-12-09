package com.huffman_algorithms;

import java.io.FileWriter;
import java.io.IOException;

public class CreateFiles {
    public static void createFileWithContent(String fileName, long size, String filePath) {
        String content = "This is a sample text. It will repeat to fill the file.\n";
        long contentLength = content.getBytes().length;

        try (FileWriter writer = new FileWriter(filePath + fileName)) {
            long writtenBytes = 0;

            while (writtenBytes < size) {
                if (writtenBytes + contentLength <= size) {
                    writer.write(content);
                    writtenBytes += contentLength;
                } else {
                    writer.write(content, 0, (int) (size - writtenBytes));
                    writtenBytes = size;
                }
            }

            System.out.println("Файл \"" + fileName + "\" создан размером " + size + " байт.");
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String fileName = "100000.txt";
        long fileSize = 100000;

        String filePath = "asdasdad";
        
        createFileWithContent(fileName, fileSize, filePath);
    }
}
