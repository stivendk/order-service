package com.example.challenge.order_service.application.service;

import com.example.challenge.order_service.application.port.ProductService;
import com.example.challenge.order_service.component.dto.OrderItemDTO;
import com.example.challenge.order_service.component.dto.ProductDTO;
import com.example.challenge.order_service.component.dto.RequestOrderItemDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateOrderItemDTO;
import com.example.challenge.order_service.component.mapper.OrderItemMapper;
import com.example.challenge.order_service.component.mapper.ProductMapper;
import com.example.challenge.order_service.domain.exception.OrderItemNotFoundException;
import com.example.challenge.order_service.domain.exception.OrderNotFoundException;
import com.example.challenge.order_service.domain.exception.InsufficientStockException;
import com.example.challenge.order_service.domain.model.Order;
import com.example.challenge.order_service.domain.model.OrderItem;
import com.example.challenge.order_service.domain.model.Product;
import com.example.challenge.order_service.infrastructure.repository.OrderItemRepository;
import com.example.challenge.order_service.infrastructure.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrderItem() {
        Long orderId = 1L;
        RequestOrderItemDTO requestOrderItemDTO = RequestOrderItemDTO.builder()
                .orderId(orderId)
                .productId(1L)
                .quantity(2)
                .build();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(100.0);
        productDTO.setStock(10);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        product.setStock(10);

        Order order = new Order();
        order.setId(orderId);

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(new Product())
                .quantity(2)
                .priceAtPurchase(200.0)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productService.findProductById(requestOrderItemDTO.getProductId())).thenReturn(productDTO);
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        when(orderItemMapper.toDTO(any(OrderItem.class))).thenReturn(new OrderItemDTO());

        OrderItemDTO result = orderItemService.addOrderItem(requestOrderItemDTO);

        assertNotNull(result);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void testAddOrderItemThrowsOrderNotFoundException() {
        Long orderId = 1L;
        RequestOrderItemDTO requestOrderItemDTO = RequestOrderItemDTO.builder()
                .productId(1L)
                .quantity(2)
                .orderId(orderId)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderItemService.addOrderItem(requestOrderItemDTO));
    }

    @Test
    void testAddOrderItemThrowsInsufficientStockException() {
        Long orderId = 1L;
        RequestOrderItemDTO requestOrderItemDTO = RequestOrderItemDTO.builder()
                .productId(1L)
                .quantity(10)
                .orderId(orderId)
                .build();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(100.0);
        productDTO.setStock(5);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        product.setStock(5);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(new Order()));
        when(productService.findProductById(requestOrderItemDTO.getProductId())).thenReturn(productDTO);
        when(productMapper.toEntity(productDTO)).thenReturn(product);

        assertThrows(InsufficientStockException.class, () -> orderItemService.addOrderItem(requestOrderItemDTO));
    }

    @Test
    void testDeleteOrderItem() {
        Long orderItemId = 1L;

        OrderItem orderItem = OrderItem.builder()
                .id(orderItemId)
                .build();

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));

        orderItemService.deleteOrderItem(orderItemId);

        verify(orderItemRepository, times(1)).delete(orderItem);
    }

    @Test
    void testDeleteOrderItemThrowsOrderItemNotFoundException() {
        Long orderItemId = 1L;

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.empty());

        assertThrows(OrderItemNotFoundException.class, () -> orderItemService.deleteOrderItem(orderItemId));
    }

    @Test
    void testGetAllOrderItems() {
        List<OrderItem> orderItems = List.of(
                OrderItem.builder().id(1L).build(),
                OrderItem.builder().id(2L).build()
        );
        when(orderItemRepository.findAll()).thenReturn(orderItems);
        when(orderItemMapper.toDTO(any(OrderItem.class))).thenReturn(new OrderItemDTO());

        List<OrderItemDTO> result = orderItemService.getAllOrderItems();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderItemRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderItem() {
        Long orderItemId = 1L;

        OrderItem orderItem = OrderItem.builder()
                .id(orderItemId)
                .build();

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        when(orderItemMapper.toDTO(orderItem)).thenReturn(new OrderItemDTO());

        OrderItemDTO result = orderItemService.getOrderItem(orderItemId);

        assertNotNull(result);
        verify(orderItemRepository, times(1)).findById(orderItemId);
    }

    @Test
    void testUpdateOrderItem() {
        Long orderItemId = 1L;
        RequestUpdateOrderItemDTO requestUpdateOrderItemDTO = RequestUpdateOrderItemDTO.builder()
                .productId(1L)
                .quantity(3)
                .build();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(100.0);
        productDTO.setStock(10);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        product.setStock(10);

        Order order = new Order();
        order.setId(1L);

        OrderItem orderItem = OrderItem.builder()
                .id(orderItemId)
                .order(order)
                .product(new Product())
                .quantity(2)
                .priceAtPurchase(200.0)
                .build();

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        when(productService.findProductById(requestUpdateOrderItemDTO.getProductId())).thenReturn(productDTO);
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        when(orderItemMapper.toDTO(any(OrderItem.class))).thenReturn(new OrderItemDTO());

        OrderItemDTO result = orderItemService.updateOrderItem(orderItemId, requestUpdateOrderItemDTO);

        assertNotNull(result);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void testUpdateOrderItemThrowsInsufficientStockException() {
        Long orderItemId = 1L;
        RequestUpdateOrderItemDTO requestUpdateOrderItemDTO = RequestUpdateOrderItemDTO.builder()
                .productId(1L)
                .quantity(10)
                .build();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(100.0);
        productDTO.setStock(5);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        product.setStock(5);

        Order order = new Order();
        order.setId(1L);

        OrderItem orderItem = OrderItem.builder()
                .id(orderItemId)
                .order(order)
                .product(new Product())
                .quantity(2)
                .priceAtPurchase(200.0)
                .build();

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        when(productService.findProductById(requestUpdateOrderItemDTO.getProductId())).thenReturn(productDTO);
        when(productMapper.toEntity(productDTO)).thenReturn(product);

        assertThrows(InsufficientStockException.class, () -> orderItemService.updateOrderItem(orderItemId, requestUpdateOrderItemDTO));
    }
}
