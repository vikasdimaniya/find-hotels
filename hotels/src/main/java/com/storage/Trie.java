package com.storage;

public class Trie {
    TrieNode root;

    public Trie() { root = new TrieNode(); }

    // Function to insert a key into the Trie
    public void insert(String key)
    {
        TrieNode currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 0;
            if (currentNode.childNode[index] == null) {
                currentNode.childNode[index]
                    = new TrieNode();
            }
            currentNode = currentNode.childNode[index];
        }
        currentNode.wordEnd = true;
    }

    // Function to search for a key in the Trie
    public boolean search(String key)
    {
        TrieNode currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 0;
            if (currentNode.childNode[index] == null) {
                return false;
            }
            currentNode = currentNode.childNode[index];
        }
        return currentNode.wordEnd;
    }
    // create a function that return all the words that start with the given prefix
    public void suggestWords(String prefix) {
        TrieNode currentNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 0;
            if (currentNode.childNode[index] == null) {
                System.out.println("No words found with this prefix");
                return;
            }
            currentNode = currentNode.childNode[index];
        }
        printAllWords(currentNode, prefix);
    }
    public void printAllWords(TrieNode node, String prefix) {
        if (node.wordEnd) {
            System.out.println(prefix);
        }
        for (int i = 0; i < 128; i++) {
            if (node.childNode[i] != null) {
                printAllWords(node.childNode[i], prefix + (char)(i));
            }
        }
    }
}

