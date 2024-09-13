package com.example.challenge.order_service.infrastructure.repository;

import com.example.challenge.order_service.domain.enums.OrderStatus;
import com.example.challenge.order_service.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByStatusIn(List<OrderStatus> statuses);

    List<Order> findByStatusNot(OrderStatus status);
}
