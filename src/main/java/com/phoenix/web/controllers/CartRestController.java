package com.phoenix.web.controllers;

import com.phoenix.data.dtos.CartRequestDto;
import com.phoenix.data.dtos.CartResponseDto;
import com.phoenix.data.dtos.CartUpdateDto;
import com.phoenix.services.cart.CartService;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    CartService cartService;

    @PostMapping("")
    public ResponseEntity<?> addItemToCart(@RequestBody CartRequestDto cartRequestDto){
        try {
            CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);
            return  ResponseEntity.ok(cartResponseDto);
        } catch (UserNotFoundException | ProductDoesNotExistException | BusinessLogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping("")
    public ResponseEntity<?> updateCartItem(@RequestBody CartUpdateDto cartUpdateDto){
        try {
            CartResponseDto cartResponseDto = cartService.updateCartItems(cartUpdateDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartResponseDto);
        } catch (UserNotFoundException | BusinessLogicException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> viewCart(@PathVariable Long userId){
        try{
            CartResponseDto cartResponseDto =  cartService.viewCart(userId);
            return ResponseEntity.status(HttpStatus.OK).body(cartResponseDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
