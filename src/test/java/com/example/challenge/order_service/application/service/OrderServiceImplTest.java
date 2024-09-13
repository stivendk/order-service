package com.example.challenge.order_service.application.service;

import com.example.challenge.order_service.application.port.ProductService;
import com.example.challenge.order_service.component.dto.*;
import com.example.challenge.order_service.component.mapper.OrderItemMapper;
import com.example.challenge.order_service.component.mapper.OrderMapper;
import com.example.challenge.order_service.domain.enums.OrderStatus;
import com.example.challenge.order_service.domain.exception.OrderExistException;
import com.example.challenge.order_service.domain.exception.OrderNotFoundException;
import com.example.challenge.order_service.domain.model.Order;
import com.example.challenge.order_service.domain.model.OrderItem;
import com.example.challenge.order_service.domain.model.Product;
import com.example.challenge.order_service.infrastructure.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        RequestOrderDTO requestOrderDTO = new RequestOrderDTO();
        Order order = new Order();
        Order savedOrder = new Order();
        OrderDTO orderDTO = new OrderDTO();

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderMapper.toDTO(savedOrder)).thenReturn(orderDTO);

        OrderDTO result = orderService.createOrder(requestOrderDTO);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderMapper, times(1)).toDTO(savedOrder);
    }

    @Test
    void testUpdateOrder() {
        Long orderId = 1L;
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        RequestUpdateOrderDTO requestUpdateOrderDTO = new RequestUpdateOrderDTO();
        requestUpdateOrderDTO.setIsPendingPaymentUpdate(true);
        orderItem.setPriceAtPurchase(50.0);
        order.setItems(Collections.singletonList(orderItem));
        order.setStatus(OrderStatus.NEW_ORDER); // Inicialización del estado

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDTO(order)).thenReturn(new OrderDTO());

        OrderDTO result = orderService.updateOrder(orderId, requestUpdateOrderDTO);

        assertNotNull(result);
        assertEquals(OrderStatus.PENDING_PAYMENT, order.getStatus());
        assertEquals(50.0, order.getTotalAmount(), 0.001);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).toDTO(order);
    }

    @Test
    void testUpdateOrderWhenPaid() {
        Long orderId = 1L;
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        RequestUpdateOrderDTO requestUpdateOrderDTO = new RequestUpdateOrderDTO();
        requestUpdateOrderDTO.setIsPendingPaymentUpdate(false);
        orderItem.setQuantity(5);
        orderItem.setPriceAtPurchase(50.0);

        // Inicializa el producto y asigna a OrderItem
        Product product = new Product();
        product.setId(1L);
        product.setStock(100);
        orderItem.setProduct(product);

        order.setItems(Collections.singletonList(orderItem));
        order.setStatus(OrderStatus.NEW_ORDER);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setStock(100);

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setQuantity(5);
        orderItemDTO.setProduct(productDTO);
        orderItemDTO.setPriceAtPurchase(50.0);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setItems(Collections.singletonList(orderItemDTO));
        orderDTO.setStatus(OrderStatus.NEW_ORDER);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);
        when(productService.findProductById(anyLong())).thenReturn(productDTO);

        orderService.updateOrder(orderId, requestUpdateOrderDTO);

        verify(productService, times(1)).updateStockProduct(anyLong(), anyInt());
    }


    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));
        when(orderMapper.toDTO(order1)).thenReturn(new OrderDTO());
        when(orderMapper.toDTO(order2)).thenReturn(new OrderDTO());

        List<OrderDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
        verify(orderMapper, times(1)).toDTO(order1);
        verify(orderMapper, times(1)).toDTO(order2);
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(new OrderDTO());

        OrderDTO result = orderService.getOrderById(orderId);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderMapper, times(1)).toDTO(order);
    }

    @Test
    void testGetOrderByIdNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderId));

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderMapper, times(0)).toDTO(any());
    }

}
