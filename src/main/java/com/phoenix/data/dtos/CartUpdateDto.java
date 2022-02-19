package com.phoenix.data.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartUpdateDto {
    private Long userId;

    private Long itemId;

    private QuantityOperation quantityOp;
}
