package com.phoenix.data.models;

import com.phoenix.data.repositories.CartRepository;
import com.phoenix.data.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Sql(scripts = "/db/insert.sql")
@Slf4j
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Adding product to cart test")
    void addProductToCartTest(){
        Product product = productRepository.findByName("Macbook Air").orElse(null);
        assertThat(product).isNotNull();


        Item item = new Item(product,2);
        Cart cart = new Cart();
        cart.addItem(item);

        Cart cartQuery = cartRepository.save(cart);
        assertThat(cartQuery.getId()).isNotNull();
        assertThat(cart.getItems().get(0).getProduct()).isNotNull();
        log.info("The object saved :: {}", cart);
    }
}