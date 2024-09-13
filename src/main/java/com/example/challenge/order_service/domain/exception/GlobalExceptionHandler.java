package com.example.challenge.order_service.domain.exception;

import com.example.challenge.order_service.component.dto.ResponseDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseDTO<?>> handleValidationException(ValidationException ex) {
        ResponseDTO<?> errorResponse = new ResponseDTO<>(null , ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ResponseDTO<?>> handleInsufficientStockException(InsufficientStockException ex) {
        ResponseDTO<?> errorResponse = new ResponseDTO<>(null , ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderExistException.class)
    public ResponseEntity<ResponseDTO<?>> handleOrderExistException(OrderExistException ex) {
        ResponseDTO<?> errorResponse = new ResponseDTO<>(null, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleOrderItemNotFoundException(OrderItemNotFoundException ex) {
        ResponseDTO<?> errorResponse = new ResponseDTO<>(null, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleOrderNotFoundException(OrderNotFoundException ex) {
        ResponseDTO<?> errorResponse = new ResponseDTO<>(null, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<ResponseDTO<?>> handleProductNotAvailableException(ProductNotAvailableException ex) {
        ResponseDTO<?> errorResponse = new ResponseDTO<>(null, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleProductNotFoundException(ProductNotFoundException ex) {
        ResponseDTO<?> errorResponse = new ResponseDTO<>(null, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
