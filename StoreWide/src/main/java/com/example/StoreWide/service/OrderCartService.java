package com.example.StoreWide.service;


import com.example.StoreWide.dto.OrderCartDto;
import com.example.StoreWide.dto.ProductDto;
import com.example.StoreWide.dto.ProductListDto;
import com.example.StoreWide.dto.TransactionDto;
import com.example.StoreWide.entity.Product;
import com.example.StoreWide.entity.Transaction;
import com.example.StoreWide.model.GlobalResponse;
import com.example.StoreWide.repository.ProductRepository;
import com.example.StoreWide.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class OrderCartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private List<OrderCartDto> orderCart = new ArrayList<>();

    // Add product to the cart
    public GlobalResponse addProductToCart(Long productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Check if the product is available
            if (product.getQuantity() < quantity) {
                return new GlobalResponse("ERROR", "Not enough product stock", null);
            }

            // Add to orderCartDTO
            OrderCartDto orderCartDTO = new OrderCartDto(product, quantity, true);
            orderCart.add(orderCartDTO);

            return new GlobalResponse("SUCCESS", "Product added to cart successfully", orderCartDTO);
        } else {
            return new GlobalResponse("ERROR", "Product not found with id: " + productId, null);
        }
    }

    // Select or deselect product in cart
    public GlobalResponse selectProduct(Long productId, boolean isSelected) {
        for (OrderCartDto cartItem : orderCart) {
            if (cartItem.getProduct().getId().equals(productId)) {
                cartItem.setSelected(isSelected);
                return new GlobalResponse("SUCCESS", "Product selection updated", cartItem);
            }
        }
        return new GlobalResponse("ERROR", "Product not found in cart", null);
    }

    // Get total cart price
    public GlobalResponse getTotalCartPrice() {

        double totalPrice = 0.0;

        for (OrderCartDto cartItem : orderCart) {
            if (cartItem.isSelected()) {
                totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
            }
        }

        return new GlobalResponse("SUCCESS", "Total price calculated", totalPrice);
    }

    // Place an order
    public GlobalResponse placeOrder() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (orderCart.isEmpty()) {
            return new GlobalResponse("ERROR", "No items in cart", null);
        }

        Transaction transaction = new Transaction();
        transaction.setTotalAmount(orderCart.stream()
                .filter(OrderCartDto::isSelected)
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum());

        transaction.setCreated(timestamp);

        Map<Long, Integer> productQuantities = new HashMap<>();

        for (OrderCartDto item : orderCart) {
            if (item.isSelected()) {
                Long productId = item.getProduct().getId();
                productQuantities.put(productId, item.getQuantity());

                // Kurangi quantity produk di inventory
                Product product = productRepository.getReferenceById(productId);
                product.setQuantity(product.getQuantity() - item.getQuantity());
                productRepository.save(product);
            }
        }

        transaction.setProductQuantities(productQuantities);
        transactionRepository.save(transaction);
        orderCart.clear();

        // Prepare response
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTotalAmount(transaction.getTotalAmount());
        transactionDto.setProductQuantities(productQuantities);

        return new GlobalResponse("SUCCESS", "Order placed successfully", transactionDto);
    }






    public List<OrderCartDto> getAllCarts() {
        // Logika untuk mengambil semua item cart
        return orderCart.stream()
                .map(item -> new OrderCartDto(item.getProduct(), item.getQuantity(), item.isSelected()))
                .collect(Collectors.toList());
    }

}
