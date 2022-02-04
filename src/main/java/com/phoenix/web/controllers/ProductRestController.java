package com.phoenix.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.phoenix.data.dtos.ProductDto;
import com.phoenix.data.models.Product;
import com.phoenix.services.product.ProductService;
import com.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {
    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> findAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }
    @PostMapping("/newproducts")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        try {
            Product product = productService.createProduct(productDto);
            return ResponseEntity.ok().body(product);
        }
        catch (IllegalArgumentException | BusinessLogicException e){
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PatchMapping("/oldproducts/{productName}")
    public ResponseEntity<?> updateProduct(@PathVariable String productName, @RequestBody ProductDto productDto){
        try {
            Product product = productService.updateProduct(productName, productDto);
            return ResponseEntity.accepted().body(product);
        }
        catch (BusinessLogicException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody JsonPatch productPatch){
        try{
           Product updatedProduct = productService.updateProductDetails(id, productPatch);
           return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }catch (BusinessLogicException | JsonPatchException | JsonProcessingException e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
//        return null;
    }
}
