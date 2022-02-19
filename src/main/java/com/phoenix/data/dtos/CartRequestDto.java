package com.phoenix.data.dtos;

import com.phoenix.data.models.Item;
import lombok.Builder;
import lombok.Data;


@Data
public class CartRequestDto {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
