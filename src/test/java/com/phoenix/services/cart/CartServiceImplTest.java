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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Sql("/db/insert.sql")
class CartServiceImplTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartService cartService;

    CartUpdateDto cartUpdateDto;

    @BeforeEach
    void setUp() {
        cartUpdateDto = CartUpdateDto.builder()
                .itemId(133L)
                .userId(5005L)
                .quantityOp(QuantityOperation.INCREASE).build();
    }

    @Test
    void addItemToCartTest() {
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setQuantity(1);
        cartRequestDto.setUserId(5011L);

        //check if user exist
        AppUser existingUser = appUserRepository.findById(cartRequestDto.getUserId()).orElse(null);
        assertThat(existingUser).isNotNull();

        //get user cart
        Cart myCart = existingUser.getMyCart();
        assertThat(myCart).isNotNull();

        //check if product exist
        Product existingProduct = productRepository.findById(cartRequestDto.getProductId()).orElse(null);
        assertThat(existingProduct).isNotNull();

        assertThat(existingProduct.getQuantity()).isGreaterThanOrEqualTo(cartRequestDto.getQuantity());

        //create Item
        Item cartItem = new Item(existingProduct, cartRequestDto.getQuantity());

        //add to cart
        myCart.addItem(cartItem);

        //save cart
        cartRepository.save(myCart);

        assertThat(myCart.getItems().size()).isEqualTo(1);

    }

    @Test
    @DisplayName("Add item to cart test")
    void addItemsToCartTest() throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setUserId(5011L);
        cartRequestDto.setQuantity(1);

        CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);

        assertThat(cartResponseDto.getItems()).isNotNull();
        assertThat(cartResponseDto.getItems().size()).isEqualTo(1);
        assertThat(cartResponseDto.getTotalPrice()).isNotEqualTo(0.0);
    }

    @Test
    @DisplayName("The total price of a cart can be updated")
    void updateCartPriceTest() throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setUserId(5011L);
        cartRequestDto.setQuantity(2);

        CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);

        assertThat(cartResponseDto.getItems()).isNotNull();
        assertThat(cartResponseDto.getItems().size()).isEqualTo(1);
        assertThat(cartResponseDto.getTotalPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Increase cart Item quantity test")
    void updateCartItemTest() throws UserNotFoundException, BusinessLogicException {

        AppUser appUser = appUserRepository.findById(5005L).orElse(null);
        assertThat(appUser).isNotNull();

        Cart userCart = appUser.getMyCart();

        Item item = null;

        //get the item
        Predicate<Item> itemPredicate = i -> i.getId().equals(cartUpdateDto.getItemId());
        Optional<Item> optionalItem = userCart.getItems().stream().filter(itemPredicate).findFirst();

//        for(int i = 0; i < userCart.getItems().size(); i++){
//            item = userCart.getItems().get(i);
//            if (item.getId().equals(cartUpdateDto.getItemId())){
//                break;
//            }
//        }

         item = optionalItem.get();

         log.info("item -> {}", item);
        assertThat(item).isNotNull();
        assertThat(item.getQuantityAdded()).isEqualTo(1);
        log.info("Cart update obj -> {}", userCart);

        CartResponseDto responseDto = cartService.updateCartItems(cartUpdateDto);
        assertThat(responseDto.getItems()).isNotNull();
        assertThat(responseDto.getItems().get(2)
                .getQuantityAdded()).isEqualTo(2);
        log.info("cart details after updating item 133 -> {}", responseDto);

    }

    @Test
    void viewCart() {
    }
}