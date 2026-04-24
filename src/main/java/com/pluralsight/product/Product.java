package com.pluralsight.product;

public class Product {
    private String productName;
    private double price;
    private String category;

    // Constructor
    public Product(String productName, double price, String category){
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString(){
        return productName + " | $" + price + " | " + category;
    }
}
