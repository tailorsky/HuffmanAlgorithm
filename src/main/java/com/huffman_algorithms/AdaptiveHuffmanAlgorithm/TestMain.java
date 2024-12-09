package com.huffman_algorithms.AdaptiveHuffmanAlgorithm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestMain {
    public static void main(String[] args) {
        fileCodecDigitalTest("example.png", "shhhh.huf", "shhhhdecoded.jpg");
    }

    public static void fileCodecDigitalTest(String filePath, String huffmanFilePath, String decodedFilePath) {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(huffmanFilePath));
            HuffmanEncoderStream huffmanEncoderStream = new HuffmanEncoderStream(new EncodingModelRefreshing(), bufferedOutputStream);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[2048];
            int read;
            while ((read = bis.read(buffer, 0, buffer.length)) > 0) {
                huffmanEncoderStream.write(buffer, 0, read);
            }
            bis.close();
            huffmanEncoderStream.close();

            BufferedOutputStream decodeOutput = new BufferedOutputStream(new FileOutputStream(decodedFilePath));
            HuffmanDecoderStream huffmanDecoderStream = new HuffmanDecoderStream(new EncodingModelRefreshing(), decodeOutput);
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(huffmanFilePath));
            int readNum;
            while ((readNum = inputStream.read(buffer)) != -1) {
                huffmanDecoderStream.write(buffer, 0, readNum);
            }
            decodeOutput.close();
            huffmanDecoderStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileEncodeTest(String filePath, String huffmanFilePath){
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(huffmanFilePath));
            HuffmanEncoderStream huffmanEncoderStream = new HuffmanEncoderStream(new EncodingModelRefreshing(), bufferedOutputStream);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[2048];
            int read;
            while ((read = bis.read(buffer, 0, buffer.length)) > 0) {
                huffmanEncoderStream.write(buffer, 0, read);
            }
            bis.close();
            huffmanEncoderStream.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void fileDecodeTest(String huffmanFilePath, String decodedFilePath){
        try{
            byte[] buffer = new byte[2048];
            BufferedOutputStream decodeOutput = new BufferedOutputStream(new FileOutputStream(decodedFilePath));
            HuffmanDecoderStream huffmanDecoderStream = new HuffmanDecoderStream(new EncodingModelRefreshing(), decodeOutput);
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(huffmanFilePath));
            int readNum;
            while ((readNum = inputStream.read(buffer)) != -1) {
                huffmanDecoderStream.write(buffer, 0, readNum);
            }
            decodeOutput.close();
            huffmanDecoderStream.close();
            inputStream.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
