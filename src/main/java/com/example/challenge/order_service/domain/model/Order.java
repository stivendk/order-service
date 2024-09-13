package com.example.challenge.order_service.domain.model;

import com.example.challenge.order_service.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tbl_orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents an order with its details and associated items.")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    @Column(name = "total_amount")
    @Schema(description = "Total amount for the order", example = "199.99")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Current status of the order", example = "NEW_ORDER")
    private OrderStatus status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Timestamp when the order was created", example = "2024-09-12T14:30:00")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Timestamp when the order was last updated", example = "2024-09-12T15:00:00")
    private LocalDateTime updatedAt;

    @Valid
    @NotNull
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    @Builder.Default
    @Schema(description = "List of items in the order")
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.totalAmount = 0.0;
        this.status = OrderStatus.NEW_ORDER;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
