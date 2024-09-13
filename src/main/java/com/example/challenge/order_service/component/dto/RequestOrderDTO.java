package com.example.challenge.order_service.component.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for a request to create an order.")
public class RequestOrderDTO {

    @Schema(description = "List of items to be included in the order")
    private List<OrderItemDTO> orderItems;
}
