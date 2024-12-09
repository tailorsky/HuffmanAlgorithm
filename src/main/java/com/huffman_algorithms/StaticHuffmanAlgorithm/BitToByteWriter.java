package com.huffman_algorithms.StaticHuffmanAlgorithm;

import java.io.IOException;
import java.io.OutputStream;

public class BitToByteWriter implements AutoCloseable{
    int bitBuffer;
    int bitWritten;
    int bytesWritten;

    OutputStream target;

    public BitToByteWriter(OutputStream target){
        this.target = target;
    }

    public void close() throws IOException{
        if ((bitWritten & 0b111) != 0){
            target.write(bitBuffer);
            bytesWritten += 1;
        }
        target.flush();
        target.close();
    }

    public void writeBit(int bit) throws IOException{
        int bitIndex = bitWritten & 0b111;
        int mask = 1 << bitIndex;

        switch (bit) {
            case 1:
                bitBuffer |= mask;
                break;
            case 0:
                bitBuffer &= ~mask;
                break;
        }
        bitWritten += 1;
        if (bitIndex + 1 == 8){
            target.write(bitBuffer);
            bytesWritten += 1;
        }
    }

    public void writeByte(int val) throws IOException {
        int mask = 1;
        for (int i = 0; i < 8; i++){
            writeBit((val & mask) > 0 ? 1 : 0);
            mask <<= 1;
        }
    }
}
