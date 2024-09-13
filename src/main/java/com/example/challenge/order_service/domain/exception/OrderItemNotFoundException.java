package com.example.challenge.order_service.domain.exception;

public class OrderItemNotFoundException extends RuntimeException {
    
    public OrderItemNotFoundException(Long id) {
        super("Order item id " + id + " not found");
    }
}
