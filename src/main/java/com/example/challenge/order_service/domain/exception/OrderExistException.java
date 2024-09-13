package com.example.challenge.order_service.domain.exception;

public class OrderExistException extends RuntimeException {

    public OrderExistException() {
        super("There is already an active order in the system. Please complete the existing order before creating a new one.");
    }
}
