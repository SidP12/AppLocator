package com.example.myapplication;

public class Product {
    private String productName;
    private String pictureOfProduct;
    private String price;
    private String availability;
    private String SKU;
    private String location;
    private String tags;
    private String review;

    public Product()  {
    }

    public Product(String productName, String pictureOfProduct, String availability, String price, String SKU, String tags, String review)  {
        this.productName = productName;
        this.pictureOfProduct = pictureOfProduct;
        this.availability = availability;
        this.price = price;
        this.SKU = SKU;
        this.tags = tags;
        this.review = review;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPictureOfProduct() {
        return pictureOfProduct;
    }

    public void setPictureOfProduct(String pictureOfProduct) {
        this.pictureOfProduct = pictureOfProduct;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
