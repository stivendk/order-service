package com.example.challenge.order_service.component.dto;

import com.example.challenge.order_service.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for a request product.")
public class RequestProductDTO {

    @Schema(description = "Name of the product", example = "Laptop")
    private String name;

    @Schema(description = "Description of the product", example = "High-performance laptop with 16GB RAM")
    private String description;

    @Schema(description = "Price of the product", example = "999.99")
    private Double price;

    @Schema(description = "Stock quantity of the product", example = "50")
    private Integer stock;

    @Schema(description = "URL of the product image", example = "http://example.com/image.jpg")
    private String imageUrl;
}
