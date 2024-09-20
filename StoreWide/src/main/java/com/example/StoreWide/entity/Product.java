package com.example.StoreWide.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;  // Quantity will be reduced when ordered

    // Other fields like category, description, etc.

    public void reduceQuantity(int orderedQuantity) {
        if (this.quantity >= orderedQuantity) {
            this.quantity -= orderedQuantity;
        } else {
            throw new RuntimeException("Not enough stock for product: " + name);
        }
    }

}
