package com.phoenix.data.repositories;

import com.phoenix.data.dtos.ProductDto;
import com.phoenix.data.models.Product;
import com.phoenix.services.product.ProductService;
import com.phoenix.web.exceptions.BusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = "/db/insert.sql")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
    }
    @Test
    @DisplayName("save a new product to a database")
    void saveProductToDatabaseTest(){
        //create a product
        Product product = new Product();
        product.setName("Bamboo Chair");
        product.setDescription("world bamboo product");
        product.setPrice(3000);
        product.setQuantity(9);
        assertThat(product.getId()).isNull();
        //save product in repository
        productRepository.save(product);
        log.info("product saved :: {}", product);
        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo("Bamboo Chair");
        assertThat(product.getPrice()).isEqualTo(3000);
        assertThat(product.getDateCreated()).isNotNull();
    }

    @Test
    @DisplayName("Find an existing product from database")
    void findExistingProductFromDatabaseTest(){
        int number = 10;
        Product product = productRepository.findById(12L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Luxury Mop");
        assertThat(product.getId()).isEqualTo(12);
        assertThat(product.getPrice()).isEqualTo(2340);
        assertThat(product.getQuantity()).isEqualTo(3);

        log.info("Product retrieved :: {}", product);
    }

    @Test
    @DisplayName("Find all products in database")
    void findAllProductsTest(){
        List<Product> products = productRepository.findAll();
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("find product by name")
    void findProductByName(){

        Product product = productRepository.findByName("Luxury Mop").orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Luxury Mop");
    }

    @Test
    @DisplayName("Update an existing product")
    void updateProductById(){
        ProductDto productDto = new ProductDto();
        productDto.setPrice(4000);
        productDto.setName("Power bank");
        productDto.setQuantity(4);
        try {
            Product product = productService.updateProduct("Luxury Mop",productDto);
            assertThat(product.getName()).isEqualTo("Power bank");
            assertThat(product.getQuantity()).isEqualTo(4);
            assertThat(product.getPrice()).isEqualTo(4000);
            log.info("Product updated is :: {}", product);
        }
        catch (BusinessLogicException e){
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("Update a product attribute test")
    void updateProductAttributeTest(){
        //check that a product exists
        Product savedProduct = productRepository.findByName("Macbook Air").orElse(null);
        assertThat(savedProduct).isNotNull();
        //update product
        assertThat(savedProduct.getName()).isEqualTo("Macbook Air");
        savedProduct.setName("Macbook Air 13");
        savedProduct.setPrice(30000);
        //save product
        productRepository.save(savedProduct);
        assertThat(savedProduct.getName()).isEqualTo("Macbook Air 13");
        assertThat(savedProduct.getPrice()).isEqualTo(30000);
    }
}