package com.phoenix.services.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.phoenix.data.dtos.ProductDto;
import com.phoenix.data.models.Product;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product findProductById(Long productId) throws ProductDoesNotExistException;
    Product createProduct(ProductDto productDto) throws BusinessLogicException;
    Product updateProduct(String productName, ProductDto productDto) throws BusinessLogicException;
    Product updateProductDetails(Long productId, JsonPatch patch) throws BusinessLogicException, JsonPatchException, JsonProcessingException;
}
