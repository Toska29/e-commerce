package com.phoenix.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.data.models.Product;
import com.phoenix.data.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/insert.sql"})
class ProductRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }



    @Test
    @DisplayName("Get product api test")
    void getProductsTest() throws Exception {
        mockMvc.perform(get("/api/product")
                .contentType("application/json"))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("create a product api test")
    void createProductTest() throws Exception {
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/api/products")
                .param("name", "Bamboo chair")
                        .param("description", "The great Bamboo")
                        .param("quantity", "5");


        mockMvc.perform(request.contentType("multipart/form-data"))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    void updateProductTest() throws Exception {
        Product product = productRepository.findById(12L).orElse(null);
        assertThat(product).isNotNull();

        mockMvc.perform(patch("/api/product/12")
                .contentType("application/json-patch+json")
                .content(Files.readAllBytes(Path.of("src/main/resources/payload.json"))))
                .andExpect(status().is(200))
                .andDo(print());

        product = productRepository.findById(12L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getDescription()).isEqualTo("capacity 40kMhz");
    }

    @Test
    void testUpdateProduct() {
    }
}