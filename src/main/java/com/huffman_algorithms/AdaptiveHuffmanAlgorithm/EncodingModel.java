package com.huffman_algorithms.AdaptiveHuffmanAlgorithm;

import java.io.IOException;

public interface EncodingModel {
    void updateByCharacter(int value);

    void writeCodeForCharacter(Integer value, BitToByteWriter bitWriter) throws IOException;

    CodeTreeNode getTree();

    CodeTreeNode getEscNode();

    boolean contains(int value);
}
