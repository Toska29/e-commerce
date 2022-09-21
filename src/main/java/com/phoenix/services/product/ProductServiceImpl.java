package com.phoenix.services.product;

import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.phoenix.data.dtos.ProductDto;
import com.phoenix.data.models.Product;
import com.phoenix.data.repositories.ProductRepository;
import com.phoenix.services.cloud.CloudService;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CloudService cloudService;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long productId) throws ProductDoesNotExistException {
        if(productId == null){
            throw  new IllegalArgumentException("Id cannot be null");
        }
        Optional<Product> queryResult = productRepository.findById(productId);
        if(queryResult.isPresent()){
            return queryResult.get();
        }
        throw new ProductDoesNotExistException("Product with Id : " + productId + "Does not exists");
    }

    @Override
    public Product createProduct(ProductDto productDto) throws BusinessLogicException {
        //product dto is not null
        if(productDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if(productRepository.findByName(productDto.getName()).isPresent()){
            throw new BusinessLogicException("Product with name " + productDto.getName() + "already exists");
        }

        Product product = new Product();
        try{
            if(productDto.getImage() != null) {
                Map<?, ?> uploadResult = cloudService.upload(productDto.getImage().getBytes(), ObjectUtils.asMap(
                        "public_id", "inventory/" + productDto.getImage().getOriginalFilename(),
                        "overwrite", true
                ));
                product.setImageUrl(uploadResult.get("url").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String productName, ProductDto productDto) throws BusinessLogicException {
        Product retrievedProduct = productRepository.findByName(productName).orElse(null);
        if(retrievedProduct == null){
            throw new BusinessLogicException("product does not exist");
        }

        retrievedProduct.setName(productDto.getName());
        retrievedProduct.setQuantity(productDto.getQuantity());
        retrievedProduct.setPrice(productDto.getPrice());
        retrievedProduct.setDescription(productDto.getDescription());
        return productRepository.save(retrievedProduct);

    }

    @Override
    public Product updateProductDetails(Long productId, JsonPatch productPatch) throws BusinessLogicException, JsonPatchException, JsonProcessingException {
        Optional<Product> productQuery = productRepository.findById(productId);
        if(productQuery.isEmpty()){
            throw new BusinessLogicException("Product with ID: " + productId + " Does not exist");
        }
        Product targetProduct = productQuery.get();

        try{
            targetProduct = applyPatchToProduct(productPatch, targetProduct);
            return saveOrUpdate(targetProduct);
        }catch (JsonPatchException | JsonProcessingException | BusinessLogicException je){
            throw new BusinessLogicException("update Failed");
        }
//        return null;
    }

    private Product applyPatchToProduct(JsonPatch productPatch, Product targetProduct) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = productPatch.
                apply(objectMapper.convertValue(targetProduct, JsonNode.class));

        return objectMapper.treeToValue(patched, Product.class);
    }

    private Product saveOrUpdate(Product product) throws BusinessLogicException {
        if(product == null){
            throw new BusinessLogicException("Product can't be null");
        }
        return productRepository.save(product);
    }
}
