package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {
    @Id
    private String sku;

    @Column(name = "name")
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;



    public Product() {
    }

    public Product(String sku, String name, String description) {
        setSku(sku);
        setName(name);
        setDescription(description);
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("'sku' is a mandatory attribute of Product");
        }
        else if (sku.length() < 12 || sku.length() > 12){
            throw new IllegalArgumentException("'sku' must have 12 characters");
        }

        else if (!sku.matches(".*([a-zA-Z].*[0-9]|[0-9].*[a-zA-Z]).*")){
            throw new IllegalArgumentException("'sku' must be a alphanumeric");
        }

        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("'name' is a mandatory attribute of Product");
        }
        else if (name.length() >= 50){
            throw new IllegalArgumentException("'name' must have less or 50 characters");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("'description' is a mandatory attribute of Product");
        }
        else if (description.length() >= 1000){
            throw new IllegalArgumentException("'description' must have less than 1000 characters");
        }
        this.description = description;
    }
    @Override
    public String toString() {
        return "Product{" +
                "sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
