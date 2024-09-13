package com.example.challenge.order_service.domain.model;

import com.example.challenge.order_service.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tbl_products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents a product with its details.")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Name of the product", example = "Laptop")
    private String name;

    @NotNull
    @Schema(description = "Description of the product", example = "High-performance laptop with 16GB RAM")
    private String description;

    @NotNull
    @DecimalMin(value = "1.0", message = "The price cannot be less than 1.0")
    @DecimalMax(value = "999999999.9", message = "The price cannot be greater than 999999999.9")
    @Schema(description = "Price of the product", example = "999.99")
    private Double price;

    @NotNull
    @Schema(description = "Stock quantity of the product", example = "50")
    private Integer stock;

    @NotNull
    @Column(name = "image_url")
    @Schema(description = "URL of the product image", example = "http://example.com/image.jpg")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Current status of the product", example = "AVAILABLE")
    private ProductStatus status;

    @PrePersist
    public void prePersist() {
        this.status = ProductStatus.AVAILABLE;
    }
}
