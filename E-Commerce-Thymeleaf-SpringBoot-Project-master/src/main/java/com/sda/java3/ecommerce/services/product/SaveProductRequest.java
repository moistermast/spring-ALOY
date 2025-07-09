package com.sda.java3.ecommerce.services.product;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaveProductRequest {
    private String name;
    private String description;
    private String price;
    private String imageUrl;
    private String categoryId;
    private boolean sale = false;
    private String salePrice;
    private int views = 0;
    private boolean featured = false;
    private String featureImage;
    private LocalDateTime createdAt;
    private String color;
    private String size;
}
