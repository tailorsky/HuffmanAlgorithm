package com.huffman_algorithms.StaticHuffmanAlgorithm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class StaticHuffmanDecodingStream {
    public static void decodeHuffman(String encodedFilePath, String outputFilePath, CodeTreeNode huffmanTree) throws IOException {
        try (InputStream inputStream = new FileInputStream(encodedFilePath);
             Writer writer = new OutputStreamWriter(new FileOutputStream(outputFilePath), "UTF-8")) {

            CodeTreeNode currentNode = huffmanTree;
            int byteData;

            while ((byteData = inputStream.read()) != -1) {
                for (int i = 0; i < 8; i++) {
                    int bit = (byteData >> i) & 1;
                    currentNode = bit == 0 ? currentNode.left : currentNode.right;

                    if (currentNode.content != null) {
                        writer.write(currentNode.content);
                        currentNode = huffmanTree;
                    }
                }
            }
        }
    }
}
