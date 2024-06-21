package com.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import com.Entities.Hotel;

public class CSVFile {
    //read the file hotel_details.csv and return all the words in that file
    public static String[] getWords(String fileData) {
        //split the file data into words based on commas spaces hiphen dot and new line
        return fileData.split("[,\\s\\-\\.\\n]");
    }
    //read file function with specifc file name
    public static String readFile(String fileName){
        byte[] bytes = null;
        try{
            bytes = Files.readAllBytes(Paths.get(fileName));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void saveToCSV(List<Hotel> hotels, String fileName) {
        boolean fileExists = new File(fileName).exists();
        try (FileWriter writer = new FileWriter(fileName, true)) { // true to append
            if (!fileExists) {
                writer.append(
                        "Hotel Name,Description Heading,Description,Price,Image URL,Location,Rating,Review Count\n");
            }
            for (Hotel hotel : hotels) {
                writer.append(hotel.name).append(',')
                        .append(hotel.descriptionHeading).append(',')
                        .append(hotel.description).append(',')
                        .append(hotel.price).append(',')
                        .append(hotel.imageUrl).append(',')
                        .append(hotel.location).append(',')
                        .append(hotel.rating).append(',')
                        .append(hotel.reviewCount).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToCSVEssentials(List<Hotel> hotels, String fileName) {
        boolean fileExists = new File(fileName).exists();
        try (FileWriter writer = new FileWriter(fileName, true)) { // true to append
            if (!fileExists) {
                writer.append(
                        "name,price,location,rating\n");
            }
            for (Hotel hotel : hotels) {
                writer.append(hotel.name).append(',')
                        .append(hotel.price).append(',')
                        .append(hotel.location).append(',')
                        .append(hotel.rating).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
