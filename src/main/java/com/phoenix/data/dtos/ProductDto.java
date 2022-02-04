package com.phoenix.data.dtos;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
public class ProductDto {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String imageUrl;
}
