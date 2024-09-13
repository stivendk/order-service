package com.example.challenge.order_service.component.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for a request to create an order item.")
public class RequestOrderItemDTO {

    @Schema(description = "Unique identifier of the product", example = "1")
    private Long productId;

    @Schema(description = "Quantity of the product to be ordered", example = "2")
    private Integer quantity;

    @Schema(description = "Unique identifier of the order", example = "1")
    private Long orderId;
}
