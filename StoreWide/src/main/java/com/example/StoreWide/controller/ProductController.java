package com.example.StoreWide.controller;

import com.example.StoreWide.entity.Product;
import com.example.StoreWide.model.GlobalResponse;
import com.example.StoreWide.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<GlobalResponse> createProduct(@RequestBody Product product) {
        GlobalResponse response = productService.createProduct(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<GlobalResponse> getProductById(@PathVariable Long id) {
        GlobalResponse response = productService.getProductById(id);

        // Return different HTTP status based on the response status
        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readAll")
    public ResponseEntity<GlobalResponse> getAllProducts() {
        GlobalResponse response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // UPDATE an existing Product
    @PutMapping("/update/{id}")
    public ResponseEntity<GlobalResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {
        GlobalResponse response = productService.updateProduct(id, updatedProduct);

        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalResponse> deleteProduct(@PathVariable Long id) {
        GlobalResponse response = productService.deleteProduct(id);

        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}


