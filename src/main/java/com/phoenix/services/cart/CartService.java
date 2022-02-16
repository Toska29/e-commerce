package com.phoenix.services.cart;

import com.phoenix.data.dtos.CartDto;
import com.phoenix.data.models.Cart;

public interface CartService {
    void saveCart(CartDto cartDto);
    Cart viewCart();
}
