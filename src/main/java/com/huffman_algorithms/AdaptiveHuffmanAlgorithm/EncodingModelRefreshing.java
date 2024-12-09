package com.huffman_algorithms.AdaptiveHuffmanAlgorithm;

import java.util.*;
import java.io.*;

public class EncodingModelRefreshing implements EncodingModel{
    public final CodeTreeNode ESC_NODE = new CodeTreeNode(null, 0);

    private CodeTreeNode tree = ESC_NODE;
    private final List<CodeTreeNode> nodeList = new ArrayList<>(500);
    private final BitBuffer bitBuffer = new BitBuffer(1024);
    private final CodeTreeNode[] nodeCache = new CodeTreeNode[256];

    public EncodingModelRefreshing(){
        nodeList.add(ESC_NODE);
    }

    @Override
    public void updateByCharacter(int value){
        CodeTreeNode node = nodeCache[((byte)value) & 0xff];
        CodeTreeNode parent;
        if (node != null){
            node.weight = node.weight + 1;
            parent = node.parent;
        }
        else{
            CodeTreeNode escNodeParent = ESC_NODE.parent;
            CodeTreeNode newNode = new CodeTreeNode(value, 1, null, null, null);
            CodeTreeNode intermediate = new CodeTreeNode(null, 0, ESC_NODE, newNode, escNodeParent);
            nodeCache[((byte)value) & 0xff] = newNode;
            newNode.parent = intermediate;
            if (escNodeParent != null){
                escNodeParent.left = intermediate;
            }
            else {
                tree = intermediate;
            }

            nodeList.set(nodeList.size() - 1, intermediate);
            nodeList.add(newNode);
            nodeList.add(ESC_NODE);

            ESC_NODE.parent = intermediate;
            parent = newNode.parent;
        }

        while (parent != null) {
            parent.weight = parent.weight + 1;
            parent=parent.parent;
        }

        while (reorderNodes()) {
            tree.updateWeights();
        }
    }

    boolean reorderNodes(){
        int i = 1;
        for (; i < nodeList.size(); i++){
            if (nodeList.get(i - 1).weight < nodeList.get(i).weight){
                break;
            }
        }

        if (i != nodeList.size()){
            CodeTreeNode first = nodeList.get(i);
            int j = 0;
            for (; j < i; j++){
                if (nodeList.get(j).weight < first.weight){
                    break;
                }
            }

            CodeTreeNode firstParent = first.parent;
            CodeTreeNode second = nodeList.get(j);
            if (first.parent == second.parent){
                if (first.weight < second.weight){
                    firstParent.left = first;
                    firstParent.right = second;
                }
                else {
                    firstParent.left = second;
                    firstParent.right = first;
                }
            }
            else {
                if (second.parent != first){
                    if (second.parent.left == second){
                        second.parent.left = first;
                    }
                    else {
                        second.parent.right = first;
                    }
                    first.parent = second.parent;
                    if (firstParent.left == first) {
                        firstParent.left = second;
                    }
                    else{
                        firstParent.right = second;
                    }
                    second.parent = firstParent;
                }
            }

            nodeList.set(j, first);
            nodeList.set(i, second);
            return true;
        }
        return false;
    }

    @Override
    public void writeCodeForCharacter(Integer value, BitToByteWriter bitWriter) throws IOException{
        CodeTreeNode node;
        if (value != null){
            node = nodeCache[value.byteValue() & 0xff];
        }
        else {
            node = ESC_NODE;
        }
        bitBuffer.setCurrent(0);
        while (node.parent != null){
            if (node.parent.left == node){
                bitBuffer.append(0);
            }
            else {
                bitBuffer.append(1);
            }
            node = node.parent;
        }

        for (int i = bitBuffer.current - 1; i >= 0; i --){
            bitWriter.writeBit(bitBuffer.get(i));
        }
    }

    @Override
    public CodeTreeNode getTree(){
        return tree;
    }

    @Override
    public CodeTreeNode getEscNode(){
        return ESC_NODE;
    }

    @Override
    public boolean contains(int value){
        return nodeCache[((byte)value) & 0xff] != null;
    }
}
