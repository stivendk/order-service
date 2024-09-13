package com.example.challenge.order_service.component.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for an item within an order.")
public class OrderItemDTO {

    @Schema(description = "Unique identifier of the order item", example = "1")
    private Long id;

    @Schema(description = "Quantity of the product ordered", example = "2")
    private Integer quantity;

    @Schema(description = "Price of the product at the time of purchase", example = "99.99")
    private Double priceAtPurchase;

    @Schema(description = "Product associated with this order item")
    private ProductDTO product;
}
