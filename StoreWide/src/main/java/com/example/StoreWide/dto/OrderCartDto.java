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

    private Product product;
    private int quantity;
    private boolean selected;

}

