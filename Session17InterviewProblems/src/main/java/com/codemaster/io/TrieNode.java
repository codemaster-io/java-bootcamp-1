package com.codemaster.io;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> map = new HashMap<>();

    private int frequency;

    private String word;

    private List<TrieNode> top10;

    public Map<Character, TrieNode> getMap() {
        return map;
    }

    public void setMap(Map<Character, TrieNode> map) {
        this.map = map;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<TrieNode> getTop10() {
        return top10;
    }

    public void setTop10(List<TrieNode> top10) {
        this.top10 = top10;
    }
}
