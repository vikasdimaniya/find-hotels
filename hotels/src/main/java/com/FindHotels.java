package com;

import com.storage.AVLTree;
import com.storage.Node;

import java.util.Scanner;

import com.runtime.CSVFile;

// start of the application
public class FindHotels {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();   
        // read the file and get all the words
        String fileData = CSVFile.readFile("hotel_details.csv");
        String[] words = CSVFile.getWords(fileData);
        System.out.println("Words in the file: ");
        for (String word : words) {
            System.out.println(word);
            tree.insertElement(word);
        }
        System.out.println("Words in the AVL tree: ");
        System.out.println(tree.getTotalNumberOfNodes());  
        
        // take user input
        Scanner sc = new Scanner(System.in); 
        String val = sc.nextLine();

        // search for the user input in the AVL tree
        Node searchedNode = tree.searchElement(val);
        System.out.println(searchedNode.frequency);
        
    }
}