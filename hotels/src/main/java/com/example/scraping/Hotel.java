package com.example.scraping;

public class Hotel {
    String name;
    String descriptionHeading;
    String description;
    String price;
    String imageUrl;
    String location;
    String rating;
    String reviewCount;
    String checkInDate;
    String checkOutDate;

    public Hotel(String name, String descriptionHeading, String description,String price, String imageUrl, String location, String rating, String reviewCount, String checkInDate, String checkOutDate) {
        this.name = name;
        this.descriptionHeading = descriptionHeading;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.location = location;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    //use this constructor if you don't have all the fields
    public Hotel(String name, String location, String price) {
        this.name = name;
        this.location = location;
        this.price = price;
    }

    //crete new constructors based on your needs
}