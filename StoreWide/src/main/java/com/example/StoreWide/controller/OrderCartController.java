package com.example.StoreWide.controller;

import com.example.StoreWide.dto.OrderCartDto;
import com.example.StoreWide.model.GlobalResponse;
import com.example.StoreWide.service.OrderCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class OrderCartController {

    @Autowired
    private OrderCartService orderCartService;

    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity<GlobalResponse> addProductToCart(
            @PathVariable Long productId,
            @PathVariable int quantity) {
        GlobalResponse response = orderCartService.addProductToCart(productId, quantity);
        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/select/{productId}")
    public ResponseEntity<GlobalResponse> selectProduct(
            @PathVariable Long productId,
            @RequestParam boolean isSelected) {
        GlobalResponse response = orderCartService.selectProduct(productId, isSelected);
        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/total")
    public ResponseEntity<GlobalResponse> getCartTotal() {
        GlobalResponse response = orderCartService.getTotalCartPrice();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/place")
    public ResponseEntity<GlobalResponse> placeOrder() {
        GlobalResponse response = orderCartService.placeOrder();
        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public GlobalResponse getAllCarts() {
        List<OrderCartDto> cartItems = orderCartService.getAllCarts();
        return new GlobalResponse("SUCCESS", "Cart items retrieved successfully", cartItems);
    }


}
