package com.phoenix.data.dtos;

import com.phoenix.data.models.Item;
import lombok.Data;


@Data
public class CartItemDto {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
