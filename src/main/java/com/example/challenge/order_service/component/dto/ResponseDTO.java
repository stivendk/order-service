package com.example.challenge.order_service.component.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {

    private T data;
    private String message;
}
