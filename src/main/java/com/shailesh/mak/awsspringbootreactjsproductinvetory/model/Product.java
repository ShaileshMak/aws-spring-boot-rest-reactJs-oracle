package com.shailesh.mak.awsspringbootreactjsproductinvetory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name="products")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "product_name")
    private String name;
    @Column(name="product_description")
    private String description;
    private String company;
    @Column(name = "product_category")
    private String category;
    private String price;
    @Column(name="image_url")
    private String imageUrl;

    public Product() {
    }

    public Product(String name, String description, String company, String category, String price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.company = company;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Optional<String> getImageUrl() {
        return Optional.ofNullable(imageUrl);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", desc='" + description + '\'' +
                ", company='" + company + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
