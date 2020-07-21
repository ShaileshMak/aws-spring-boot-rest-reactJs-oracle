package com.shailesh.mak.awsspringbootreactjsproductinvetory.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private String company;
    private String category;
    private String price;
    private String imageUrl;
    private MultipartFile image;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, String company, String category, String price, String imageUrl, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.company = company;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.image = image;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
