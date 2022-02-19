package com.phoenix.services.cart;

import com.phoenix.data.dtos.CartRequestDto;
import com.phoenix.data.dtos.CartResponseDto;
import com.phoenix.data.dtos.CartUpdateDto;
import com.phoenix.data.dtos.QuantityOperation;
import com.phoenix.data.models.AppUser;
import com.phoenix.data.models.Cart;
import com.phoenix.data.models.Item;
import com.phoenix.data.models.Product;
import com.phoenix.data.repositories.AppUserRepository;
import com.phoenix.data.repositories.CartRepository;
import com.phoenix.data.repositories.ProductRepository;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public CartResponseDto addItemToCart(CartRequestDto cartRequestDto) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {
        //check if user exist
       Optional<AppUser> query = appUserRepository.findById(cartRequestDto.getUserId());

       if(query.isEmpty()){
            throw  new UserNotFoundException("User with ID " + cartRequestDto.getUserId() + " not found" );
        }

        AppUser existingUser = query.get();
        //get user cart
        Cart myCart = existingUser.getMyCart();

        //check if product exist
        Product existingProduct = productRepository.findById(cartRequestDto.getProductId()).orElse(null);

        if(existingProduct == null) {
            throw new ProductDoesNotExistException("Product with ID" + cartRequestDto.getProductId() + " does not exist" );
        }

        if(!quantityIsValid(existingProduct, cartRequestDto.getQuantity())){
            throw new BusinessLogicException("Quantity too large");
        }

        //create Item
        Item cartItem = new Item(existingProduct, cartRequestDto.getQuantity());

        //add to cart
        myCart.addItem(cartItem);

        myCart.setTotalPrice(myCart.getTotalPrice() + calculateItemPrice(cartItem));

        //save cart
        cartRepository.save(myCart);

        return buildCartResponse(myCart);

    }

    private double calculateItemPrice(Item cartItem) {
        return cartItem.getProduct().getPrice() * cartItem.getQuantityAdded();
    }

    private boolean quantityIsValid(Product existingProduct, Integer quantity) {
        return  existingProduct.getQuantity() >= quantity;
    }

    private CartResponseDto buildCartResponse(Cart cart){
        return CartResponseDto.builder().
                items(cart.getItems()).
                totalPrice(cart.getTotalPrice())
                .build();
    }

    @Override
    public CartResponseDto viewCart(Long userId) throws UserNotFoundException {
        AppUser appUser = appUserRepository.findById(userId).orElse(null);
        if(appUser == null){
            throw new UserNotFoundException("User with ID " + userId + " does not exist");
        }
        Cart myCart = appUser.getMyCart();
        return buildCartResponse(myCart);

    }

    @Override
    public CartResponseDto updateCartItems(CartUpdateDto cartUpdateDto) throws UserNotFoundException, BusinessLogicException {

        //get the user by id
        Optional<AppUser> queryAppUser = appUserRepository.findById(cartUpdateDto.getUserId());

        if(queryAppUser.isEmpty()){
            throw  new UserNotFoundException("user with ID " + cartUpdateDto.getUserId() + " not found");
        }
        //get user cart

        Cart myCart = queryAppUser.get().getMyCart();
        //find item in cart
        Item item = findCartItem(cartUpdateDto.getItemId(), myCart).orElse(null);
        if(item == null) throw new BusinessLogicException("Item not in cart");
        //perform operation
        if(cartUpdateDto.getQuantityOp() == QuantityOperation.INCREASE){
            item.setQuantityAdded(item.getQuantityAdded() + 1);
            myCart.setTotalPrice(myCart.getTotalPrice() + item.getProduct().getPrice());
        }
        else if(cartUpdateDto.getQuantityOp() == QuantityOperation.DECREASE){
            item.setQuantityAdded(item.getQuantityAdded() - 1);
            myCart.setTotalPrice(myCart.getTotalPrice() - item.getProduct().getPrice());
        }
        cartRepository.save(myCart);
        return buildCartResponse(myCart);
    }

    private Optional<Item> findCartItem(Long itemId, Cart cart){
        Predicate<Item> itemPredicate = i -> i.getId().equals(itemId);
       return cart.getItems().stream().filter(itemPredicate).findFirst();

    }
}
