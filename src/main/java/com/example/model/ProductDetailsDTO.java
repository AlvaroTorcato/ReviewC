package com.example.model;

public class ProductDetailsDTO {
    String sku;
    String name;

    String description;


    public ProductDetailsDTO(String sku, String name, String description) {
        this.sku = sku;
        this.name = name;
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
