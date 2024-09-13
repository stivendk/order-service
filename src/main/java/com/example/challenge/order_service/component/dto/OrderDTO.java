package com.example.challenge.order_service.component.dto;

import com.example.challenge.order_service.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Data Transfer Object for an order.")
public class OrderDTO {

    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    @Schema(description = "Total amount for the order", example = "199.99")
    private Double totalAmount;

    @Schema(description = "Current status of the order", example = "NEW_ORDER")
    private OrderStatus status;

    @Schema(description = "List of items in the order")
    private List<OrderItemDTO> items;

    @Schema(description = "Timestamp when the order was created", example = "2024-09-12T14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the order was last updated", example = "2024-09-12T15:00:00")
    private LocalDateTime updatedAt;
}
