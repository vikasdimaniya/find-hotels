package com.Scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HotelsComScraper {

    public static void main(String[] args) {
        try {

            System.setProperty("webdriver.chrome.driver", "chromedriver");
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            WebDriver driver = new ChromeDriver();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

            // Define the search parameters
            String[] city = { "windsor", "ottawa", "toronto", "New York" };
            String[] checkinDate = { "2024-07-12", "2024-06-12", "2024-06-03", "2024-06-05" };
            String[] checkoutDate = { "2024-07-15", "2024-06-15", "2024-06-08", "2024-06-08" };
            int[] adults = { 2, 2, 2, 1 };
            int[] rooms = { 1, 2, 1, 2 };
            for (int order = 0; order < city.length; order++) {
                boolean nextCity = false;
                System.out.println("Searching for hotels on ca.hotels.com in city " + city[order] + " from " + checkinDate[order] + " to "
                        + checkoutDate[order] + " for " + adults[order] + " adults and " + rooms[order] + " rooms");
                String hotelsComUri = buildHotelsComUrl(city[order], checkinDate[order], checkoutDate[order],
                        adults[order], rooms[order]);
                // Navigate to the Hotels.com URL
                driver.get(hotelsComUri.toString());
                List<Hotel> hotels = new ArrayList<>();
                try {
                    // Perform search
                    WebElement destinationInput = driver.findElement(By.className("uitk-form-field-trigger"));
                    destinationInput.click();
                    Thread.sleep(1000);
                    WebElement destinationInput2 = driver.findElement(By.id("destination_form_field"));
                    destinationInput2.sendKeys(city[order]);
                    Thread.sleep(1000);

                    // clicking on the auto complete suggestion
                    WebElement autoCompleteInput = driver
                            .findElement(By.className("destination_form_field-result-item-button"));
                    autoCompleteInput.click();
                    Thread.sleep(1000);

                    // Clicking on search button
                    WebElement searchButton = driver.findElement(By.id("search_button"));
                    searchButton.click();

                    // Wait for the page to load and results to be visible
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

                    Thread.sleep(5000); // Wait for the page to load
                    WebElement propertyListingResults = driver
                            .findElement(By.xpath("//div[@data-stid='property-listing-results']"));

                    // Find all child elements representing individual hotel listings
                    List<WebElement> hotelCards = propertyListingResults
                            .findElements(By.xpath(".//div[@data-stid='lodging-card-responsive']"));

                    // need this to scroll the page, otherwise the browser will not fully load all
                    // the hotel cards
                    // websites do that to save resources like memory, CPU and network bandwidth
                    JavascriptExecutor js = (JavascriptExecutor) driver;

                    // Iterate through each hotel card and extract the hotel name
                    for (WebElement hotelCard : hotelCards) {
                        js.executeScript("window.scrollBy(0, 350);");
                        String hotelName = " ";
                        try {
                            WebElement hotelNameElement = hotelCard
                                    .findElement(By.xpath(".//h3[contains(@class, 'uitk-heading')]"));
                            hotelName = hotelNameElement.getText();
                        } catch (Exception e) {
                            // Handle the exception or log it
                        }

                        // Extract the price
                        String price = "";
                        try {
                            WebElement priceElement = hotelCard
                                    .findElement(By.xpath(".//span[@aria-hidden='true']/div[contains(@class, 'uitk-text-emphasis-theme')]"));
                            String fullPriceText = priceElement.getText(); // This will get the text "CA $259"
                            price = fullPriceText.replaceAll("[^\\d]", ""); // Remove non-numeric characters to get "259"
                        } catch (Exception e) {
                            // Handle exception or add logging if needed
                        }

                        if (price.equals("")) {
                            nextCity = true;
                            continue;
                        }
                        // Extract the hotel image URL
                        String imageUrl = " ";
                        // try {
                        //     WebElement imageElement = hotelCard
                        //             .findElement(By.xpath(".//img[@class='uitk-image-media']"));
                        //     imageUrl = imageElement.getAttribute("src");
                        // } catch (Exception e) {
                        //     // Handle the exception or log it
                        // }

                        // Extract the hotel location
                        String location = " ";
                        try {
                            WebElement locationElement = hotelCard.findElement(
                                    By.xpath(".//div[contains(@class, 'uitk-type-300') and not(@aria-hidden='true')]"));
                            location = locationElement.getText();
                        } catch (Exception e) {
                            // Handle the exception or log it
                        }

                        // Extract the hotel description heading
                        String descriptionHeading = " ";
                        // try {
                        // WebElement descriptionHeadingElement = hotelCard
                        // .findElement(By.xpath(".//div[contains(@class, 'uitk-type-bold')]"));
                        // descriptionHeading = descriptionHeadingElement.getText();
                        // } catch (Exception e) {
                        // // Handle the exception or log it
                        // }

                        // Extract the hotel description
                        String description = " ";
                        // try {
                        // WebElement descriptionElement = hotelCard
                        // .findElement(By.xpath(".//div[contains(@class,
                        // 'uitk-text-default-theme')]"));
                        // description = descriptionElement.getText();
                        // } catch (Exception e) {
                        // // Handle the exception or log it
                        // }

                        // Extract the hotel rating
                        String rating = "";
                        try {
                            WebElement ratingElement = hotelCard.findElement(
                                    By.xpath(".//span[contains(@class, 'is-visually-hidden') and contains(text(), 'out of')]"));
                            String ratingText = ratingElement.getText(); // This gets the text "8.4 out of 10"
                            String numericRating = ratingText.split(" ")[0]; // Extract "8.4"
                            double ratingValue = Double.parseDouble(numericRating);
                            int percentageRating = (int) (ratingValue * 10); // Convert to percentage
                            rating = percentageRating + "%"; // "84%"
                        } catch (Exception e) {
                            // Handle the exception or log it
                        }
                        

                        // Extract the number of reviews
                        String reviewCount = " ";
                        // try {
                        //     WebElement reviewCountElement = hotelCard.findElement(
                        //             By.xpath(
                        //                     ".//span[contains(@class, 'is-visually-hidden') and contains(text(),'reviews')]"));
                        //     reviewCount = reviewCountElement.getText();
                        // } catch (Exception e) {
                        //     // Handle the exception or log it
                        // }

                        // Print the extracted information
                        System.out.println("Hotel Name: " + hotelName);
                        // System.out.println("Description Heading: " + descriptionHeading);
                        // System.out.println("Description: " + description);
                        // System.out.println("Price: " + price);
                        // System.out.println("Image URL: " + imageUrl);
                        // System.out.println("Location: " + location);
                        // System.out.println("Rating: " + rating);
                        // System.out.println("Review Count: " + reviewCount);
                        // System.out.println("------------");
                        hotels.add(new Hotel(hotelName, descriptionHeading, description, price, imageUrl, location,
                                rating, reviewCount, checkinDate[order], checkoutDate[order]));
                        js.executeScript("window.scrollBy(0, 250);");
                    }
                    if(nextCity){
                        nextCity = false;
                        continue;
                    }
                    // Save the details to a CSV file
                    saveToCSVEssentials(hotels, "hotel_details.csv");

                    // Close the WebDriver
                    //driver.quit();
                } catch (Exception e) {
                    System.out.println("MY Error: " + e);
                    // close the browser
                    driver.quit();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
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

    public static String buildHotelsComUrl(String city, String checkinDate, String checkoutDate, int adults,
            int rooms) {
        String baseUrl = "https://ca.hotels.com/Hotel-Search";
        return String.format(
                "%s?regionId=&latLong=&flexibility=0_DAY&d1=%s&startDate=%s&d2=%s&endDate=%s&adults=%d&rooms=%d",
                baseUrl, checkinDate, checkinDate, checkoutDate, checkoutDate, adults, rooms);
    }
}