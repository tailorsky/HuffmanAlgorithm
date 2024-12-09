package com.huffman_algorithms.AdaptiveHuffmanAlgorithm;

public class CodeTreeNode implements Comparable<CodeTreeNode> {
    Integer content;
    int weight;
    CodeTreeNode parent;
    CodeTreeNode left;
    CodeTreeNode right;

    public CodeTreeNode(Integer content, int weight){
        this.content = content;
        this.weight = weight;
    }

    public CodeTreeNode (Integer content, int weight, CodeTreeNode left, CodeTreeNode right, CodeTreeNode parent){
        this.content = content;
        this.weight = weight;
        this.left= left;
        this.right= right;
        this.parent = parent;
    }

    @Override
    public int compareTo(CodeTreeNode node){
        return node.weight - weight;
    }

    public int updateWeights(){
        if (content != null){
            return weight;
        }
        else{
            int w = 0;
            if (right != null){
                w += right.updateWeights();
            }
            if (left != null){
                w+=left.updateWeights();
            }
            weight = w;
            return weight;
        }
    }
}
