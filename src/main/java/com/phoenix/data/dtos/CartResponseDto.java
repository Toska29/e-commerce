package com.phoenix.data.dtos;

import com.phoenix.data.models.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CartResponseDto {
    private List<Item> items;

    private double totalPrice;
}
