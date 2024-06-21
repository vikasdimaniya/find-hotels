package com;

import com.storage.AVLTree;
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
            System.out.println(tree.getTotalNumberOfNodes());  
        }
        System.out.println("Words in the AVL tree: ");
        
    }
}