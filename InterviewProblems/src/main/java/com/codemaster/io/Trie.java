package com.codemaster.io;

import java.util.*;

public class Trie {
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String word = null; // Stores the word if this node represents the end of a word
    }

    private final TrieNode root = new TrieNode();

    // Recursively insert a word into the Trie
    public void insert(String word) {
        insertRecursive(root, word, 0);
    }

    private void insertRecursive(TrieNode node, String word, int index) {
        if (index == word.length()) {
            node.word = word; // Mark the end of the word
            return;
        }
        char c = word.charAt(index);
        node.children.putIfAbsent(c, new TrieNode());
        insertRecursive(node.children.get(c), word, index + 1);
    }

    // Recursively get all words that start with the given prefix
    public List<String> getWordsByPrefix(String prefix) {
        TrieNode node = searchPrefix(root, prefix, 0);
        List<String> results = new ArrayList<>();
        if (node != null) {
            collectWords(node, results);
        }
        return results;
    }

    private TrieNode searchPrefix(TrieNode node, String prefix, int index) {
        if (index == prefix.length()) {
            return node; // Reached the end of the prefix
        }
        char c = prefix.charAt(index);
        if (!node.children.containsKey(c)) {
            return null; // Prefix not found
        }
        return searchPrefix(node.children.get(c), prefix, index + 1);
    }

    // Recursively collect all words from the given node
    private void collectWords(TrieNode node, List<String> results) {
        if (node.word != null) {
            results.add(node.word); // Add the word if this node marks the end of a word
        }
        for (TrieNode child : node.children.values()) {
            collectWords(child, results);
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        // Example dictionary words
        List<String> dictionary = Arrays.asList("apple", "app", "ape", "apricot", "banana", "band", "bat");

        // Insert all words into the Trie
        for (String word : dictionary) {
            trie.insert(word);
        }

        // Example usage
        System.out.println(trie.getWordsByPrefix("ap"));
        System.out.println(trie.getWordsByPrefix("ba"));
    }
}
