package com.phoenix.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.data.dtos.CartRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql("/db/insert.sql")
class CartRestControllerTest {

    @Autowired
    MockMvc mockMvc;



    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Add a new Item to cart")
    void addItemToCart() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setUserId(5011L);
        cartRequestDto.setProductId(14L);
        cartRequestDto.setQuantity(1);

        String requestBody = objectMapper.writeValueAsString(cartRequestDto);

        mockMvc.perform(post("/api/cart").contentType("application/json")
                .content(requestBody)).andExpect(status().is(200))
                .andDo(print());
    }
}