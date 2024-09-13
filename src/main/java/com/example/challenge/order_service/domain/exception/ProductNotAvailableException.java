package com.example.challenge.order_service.domain.exception;

public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException() {
        super("Product not available in the requested quantity");
    }
}
