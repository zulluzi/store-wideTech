package com.example.StoreWide.dto;

import com.example.StoreWide.entity.Product;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartDto{

    private Product product;      // Reference to the product entity
    private int quantity;         // Quantity of the product in the cart
    private boolean selected;     // Whether the product is selected for purchase

    // Since we are not putting business logic in DTO, no total calculation here.
}

