package com;

import com.storage.AVLTree;
import com.storage.Node;
import com.storage.MinHeap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.runtime.CSVFile;

// start of the application
public class FindHotels {
    public static void main(String[] args) {
        
        /*
         * TASK 2 Vocabulary
         */ 

        AVLTree tree = new AVLTree();   
        
        /*
         * TASK 2 Vocabulary part A
         */
        // read the file and get all the words
        String fileData = CSVFile.readFile("hotel_details.csv");
        String[] words = CSVFile.getWords(fileData);
        //System.out.println("Words in the file: ");

        /*
         * TASK 2 Vocabulary part B
         */
        // insert all the words into the AVL tree
        for (String word : words) {
            tree.insertElement(word.toLowerCase());
        }

        System.out.println("Words in the AVL tree: "+tree.getTotalNumberOfNodes());
        
        /*
         * TASK 2 Autocomplete Functionality:
         */ 

        // taking user input for searching the word
        Scanner sc = new Scanner(System.in); 
        String val = sc.nextLine();

        // tree.preorderTraversal(); // print the AVL tree in preorder
        
        /*
         * TASK 2 Autocomplete Functionality PART A:
         */

        /*
         * search for the user input based on just the prefix in the AVL tree
         * This is different from normal search as in we are only interested in the prefix of the word not the whole word
         * For example, if the user enters "hot", the search should return the first node starting with "hot" like "hotel", "hotels", "hotels.com" etc.
         */ 
        Node searchedNode = tree.searchPrefix(val.toLowerCase());
        if(searchedNode == null) {
            System.out.println("The word is not found in the AVL tree");
            return;
        }
        //System.out.println(searchedNode.frequency);

        // print all the children of the searched node
        // System.out.println("Children of the searched node: ");
        // tree.inorderTraversal(searchedNode);

        /*
         * TASK 2 Autocomplete Functionality PART B:
         */

        MinHeap minHeap = new MinHeap();
        minHeap.createHeapFromTree(searchedNode);
        
        ArrayList<Node> suggestions = new ArrayList<Node>();
        //Extract elements from the heap
        while (!minHeap.isEmpty()) {
            Node minNode = minHeap.extractMin();
            suggestions.add(minNode);
            // System.out.println("Suggestion: " + minNode.element + " (" + minNode.frequency + ")");
        }
        suggestions.sort(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return -1*Integer.compare(n1.frequency, n2.frequency);
            }
        });

        //printing all the final suggestions based on frequency of the words
        System.out.println("Sorted Suggestions: ");
        for(Node suggestion: suggestions) {
            System.out.println(suggestion.element + " (" + suggestion.frequency + ")");
        }
    }
}