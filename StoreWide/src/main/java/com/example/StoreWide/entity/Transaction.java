package com.example.StoreWide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionId;
    private double totalAmount;

    // Field baru untuk menyimpan ID produk dan kuantitasnya
    @ElementCollection
    private Map<Long, Integer> productQuantities = new HashMap<>(); // Key: productId, Value: quantity


}
