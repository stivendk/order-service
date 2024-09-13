package com.example.challenge.order_service.component.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestUpdateOrderDTO {

    @Schema(description = "Flag indicating if the order is pending payment",
            example = "true",
            required = true)
    private Boolean isPendingPaymentUpdate;
}
