package com.phoenix.services.cart;

import com.github.fge.jsonpatch.JsonPatch;
import com.phoenix.data.dtos.CartRequestDto;
import com.phoenix.data.dtos.CartResponseDto;
import com.phoenix.data.dtos.CartUpdateDto;
import com.phoenix.data.models.Cart;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;

public interface CartService {
    CartResponseDto addItemToCart(CartRequestDto cartRequestDto) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException;
    CartResponseDto viewCart(Long userId) throws UserNotFoundException;
    CartResponseDto updateCartItems(CartUpdateDto cartUpdateDto) throws UserNotFoundException, BusinessLogicException;
}
