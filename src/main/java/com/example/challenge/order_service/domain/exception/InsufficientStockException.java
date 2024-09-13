package com.example.challenge.order_service.domain.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String productName) {
       super("Insufficient stock for product: " + productName);
    }
}
