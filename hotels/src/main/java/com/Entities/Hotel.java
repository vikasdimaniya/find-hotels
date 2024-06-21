package com.Entities;

public class Hotel {
    public String name;
    public String descriptionHeading;
    public String description;
    public String price;
    public String imageUrl;
    public String location;
    public String rating;
    public String reviewCount;
    public String checkInDate;
    public String checkOutDate;

    public Hotel(String name, String descriptionHeading, String description,String price, String imageUrl, String location, String rating, String reviewCount, String checkInDate, String checkOutDate) {
        //all commas in the fields will be replaced with a hiphen to avoid conflicts with the csv file
        this.name = name.replace(",", "-");
        this.descriptionHeading = descriptionHeading.replace(",", "-");
        this.description = description.replace(",", "-");
        this.price = price.replace(",", "-");
        this.imageUrl = imageUrl.replace(",", "-");
        this.location = location.replace(",", "-");
        this.rating = rating.replace(",", "-");
        this.reviewCount = reviewCount.replace(",", "-");
        this.checkInDate = checkInDate.replace(",", "-");
        this.checkOutDate = checkOutDate.replace(",", "-");
    }

    //use this constructor if you don't have all the fields
    public Hotel(String name, String location, String price) {
        this.name = name.replace(",", "-");
        this.location = location.replace(",", "-");
        this.price = price.replace(",", "-");
    }

    //crete new constructors based on your needs
}