package com.example.challenge.order_service.application.service;

import com.example.challenge.order_service.application.port.OrderService;
import com.example.challenge.order_service.application.port.ProductService;
import com.example.challenge.order_service.component.dto.*;
import com.example.challenge.order_service.component.mapper.OrderMapper;
import com.example.challenge.order_service.domain.enums.OrderStatus;
import com.example.challenge.order_service.domain.exception.InsufficientStockException;
import com.example.challenge.order_service.domain.exception.OrderExistException;
import com.example.challenge.order_service.domain.exception.OrderNotFoundException;
import com.example.challenge.order_service.domain.model.Order;
import com.example.challenge.order_service.domain.model.OrderItem;
import com.example.challenge.order_service.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;

    @Override
    public OrderDTO createOrder(RequestOrderDTO requestOrderDTO) {
        verifyNoActiveOrder();
        Order order = new Order();
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(Long id, RequestUpdateOrderDTO requestUpdateOrderDTO) {
        Order order = findOrderById(id);
        updateOrderDetails(order, requestUpdateOrderDTO);
        if (order.getStatus() == OrderStatus.PAID) {
            updateProductStock(order);
            createNewOrderIfNeeded();
        }
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = findOrderById(id);
        return orderMapper.toDTO(order);
    }

    @Override
    public OrderDTO getActiveOrder() {
        return activeOrder().map(orderMapper::toDTO).orElse(null);
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private void updateOrderDetails(Order order, RequestUpdateOrderDTO requestUpdateOrderDTO) {
        double totalAmount = calculateTotalAmount(order);
        order.setTotalAmount(totalAmount);
        updateOrderStatus(order, requestUpdateOrderDTO.getIsPendingPaymentUpdate());
    }

    private double calculateTotalAmount(Order order) {
        return order.getItems().stream()
                .mapToDouble(OrderItem::getPriceAtPurchase)
                .sum();
    }

    private void updateOrderStatus(Order order, Boolean isPendingPaymentUpdate) {
        order.setStatus(isPendingPaymentUpdate ? OrderStatus.PENDING_PAYMENT : OrderStatus.PAID);
    }

    private void updateProductStock(Order order) {
        order.getItems().forEach(orderItem -> {
            ProductDTO productDTO = productService.findProductById(orderItem.getProduct().getId());
            if (productDTO.getStock() < orderItem.getQuantity()) {
                throw new InsufficientStockException(productDTO.getName());
            }
            productDTO.setStock(productDTO.getStock() - orderItem.getQuantity());
            productService.updateStockProduct(productDTO.getId(), productDTO.getStock());
        });
    }

    private void verifyNoActiveOrder() {
        if (activeOrder().isPresent()) {
            throw new OrderExistException();
        }
    }

    private void createNewOrderIfNeeded() {
        if (orderRepository.findByStatusNot(OrderStatus.PAID).isEmpty()) {
            orderRepository.save(new Order());
        }
    }

    private Optional<Order> activeOrder() {
        return orderRepository.findByStatusIn(
                List.of(OrderStatus.NEW_ORDER, OrderStatus.PENDING_PAYMENT)
        );
    }
}
