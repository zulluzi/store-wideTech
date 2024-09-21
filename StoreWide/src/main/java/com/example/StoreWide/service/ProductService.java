package com.example.StoreWide.service;

import com.example.StoreWide.entity.Product;
import com.example.StoreWide.model.GlobalResponse;
import com.example.StoreWide.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public GlobalResponse createProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findByNameIgnoreCase(product.getName());

        if (existingProduct.isPresent()) {
            return new GlobalResponse("ERROR", "Product is exist, please do the update", null);
        }

        Product createdProduct = productRepository.save(product);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        createdProduct.setCreated(timestamp);

        productRepository.save(createdProduct);

        return new GlobalResponse("SUCCESS", "Product created successfully", createdProduct);
    }


    public GlobalResponse getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return new GlobalResponse("SUCCESS", "Product retrieved successfully", productOptional.get());
        } else {
            return new GlobalResponse("ERROR", "Product not found with id: " + id, null);
        }
    }

    public GlobalResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        return new GlobalResponse("SUCCESS", "All products retrieved successfully", products);
    }

    public GlobalResponse updateProduct(Long id, Product updatedProduct) {
        Optional<Product> productOptional = productRepository.findById(id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setUpdated(timestamp);

            Product savedProduct = productRepository.save(existingProduct);
            return new GlobalResponse("SUCCESS", "Product updated successfully", savedProduct);
        } else {
            return new GlobalResponse("ERROR", "Product not found with id: " + id, null);
        }
    }

    public GlobalResponse deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
            return new GlobalResponse("SUCCESS", "Product deleted successfully", null);
        } else {
            return new GlobalResponse("ERROR", "Product not found with id: " + id, null);
        }
    }
}
