package com.example.challenge.order_service.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents an item within an order.")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the order item", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Quantity of the product ordered", example = "2")
    private Integer quantity;

    @NotNull
    @Column(name = "price_at_purchase")
    @Schema(description = "Price of the product at the time of purchase", example = "99.99")
    private Double priceAtPurchase;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    @JsonIgnore
    @Schema(description = "Order to which this item belongs")
    private Order order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Schema(description = "Product associated with this order item")
    private Product product;
}
