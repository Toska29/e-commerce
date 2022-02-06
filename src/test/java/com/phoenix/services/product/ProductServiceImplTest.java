package com.phoenix.services.product;

import com.phoenix.data.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class ProductServiceImplTest {

    @Test
    void applyPatchToProductTest(){
        Product product = new Product();
        product.setName("Table top");
        product.setPrice(5050);
        product.setDescription("Table top for kitchen");

    }

}