package com.example.challenge.order_service.infrastructure.repository;

import com.example.challenge.order_service.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByProductIdAndOrderId(Long productId, Long orderId);
}
