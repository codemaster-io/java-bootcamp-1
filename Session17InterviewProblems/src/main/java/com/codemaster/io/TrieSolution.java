package com.codemaster.io;

import java.util.ArrayList;
import java.util.List;

public class TrieSolution {

    private TrieNode root;

    public TrieSolution() {
        root = new TrieNode();

    }

    public void insert(String word, int frequency) {
        insertRecursive(root, 0, word, frequency);
    }

    public TrieNode insertRecursive(TrieNode curNode, int pos, String word, int frequency) {

        if(pos == word.length()-1) {
            curNode.setWord(word);
            curNode.setFrequency(frequency);
            return curNode;
        }

        char ch = word.charAt(pos);
        TrieNode childNode;

        if(curNode.getMap().containsKey(ch)) {
            childNode = insertRecursive(curNode.getMap().get(ch), pos + 1, word, frequency);
        } else {
            TrieNode newNode = new TrieNode();
            curNode.getMap().put(ch, newNode);
            childNode = insertRecursive(newNode, pos + 1, word, frequency);
        }

        for(TrieNode topNode : curNode.getTop10()) {
            // compare with current word frequency
            // insert delete
        }

        return childNode;
    }


    public List<String> words(String prefix) {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        TrieSolution trieSolution = new TrieSolution();

        trieSolution.insert("hello", 5);
    }
}
