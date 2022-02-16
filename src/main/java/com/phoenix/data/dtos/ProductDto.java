package com.phoenix.data.dtos;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String imageUrl;
    private MultipartFile image;
}
