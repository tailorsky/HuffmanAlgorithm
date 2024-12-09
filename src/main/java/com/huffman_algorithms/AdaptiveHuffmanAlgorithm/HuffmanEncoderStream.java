package com.huffman_algorithms.AdaptiveHuffmanAlgorithm;

import java.io.IOException;
import java.io.OutputStream;

public class HuffmanEncoderStream extends OutputStream{
    EncodingModel encodingModel;

    BitToByteWriter bitWriter;

    public HuffmanEncoderStream(OutputStream outputStream){
        this.encodingModel = new EncodingModelRefreshing();
        bitWriter = new BitToByteWriter(outputStream);
    }

    public HuffmanEncoderStream(EncodingModel encodingModel, OutputStream outputStream){
        this.encodingModel = encodingModel;
        bitWriter = new BitToByteWriter(outputStream);
    }

    @Override
    public void write(int b) throws IOException{
        if (encodingModel.contains(b)) {
            encodingModel.writeCodeForCharacter(b, bitWriter);
        }
        else {
            encodingModel.writeCodeForCharacter(null, bitWriter);
            bitWriter.writeByte(b);
        }
        encodingModel.updateByCharacter(b);
    }

    @Override
    public void close() throws IOException{
        encodingModel.writeCodeForCharacter(null, bitWriter);
        bitWriter.close();
    }
}
