package com.example.rapidrentals.Helper;

public class CarHelper {
    private int image;
    private String brand_model;
    private String year_type;
    private String location;
    private String fuel;
    private String price;

    public CarHelper(int image, String brand_model, String year_type, String location, String fuel, String price) {
        this.image = image;
        this.brand_model = brand_model;
        this.year_type = year_type;
        this.location = location;
        this.fuel = fuel;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public String getBrand_model() {
        return brand_model;
    }

    public String getYear_type() {
        return year_type;
    }

    public String getLocation() {
        return location;
    }

    public String getFuel() {
        return fuel;
    }

    public String getPrice() {
        return price;
    }
}
