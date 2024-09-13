package com.example.challenge.order_service.infrastructure.repository;

import com.example.challenge.order_service.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
