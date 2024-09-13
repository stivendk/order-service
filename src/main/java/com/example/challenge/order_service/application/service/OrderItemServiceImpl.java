package com.example.challenge.order_service.application.service;

import com.example.challenge.order_service.application.port.OrderItemService;
import com.example.challenge.order_service.application.port.ProductService;
import com.example.challenge.order_service.component.dto.OrderItemDTO;
import com.example.challenge.order_service.component.dto.ProductDTO;
import com.example.challenge.order_service.component.dto.RequestOrderItemDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateOrderItemDTO;
import com.example.challenge.order_service.component.mapper.OrderItemMapper;
import com.example.challenge.order_service.component.mapper.ProductMapper;
import com.example.challenge.order_service.domain.exception.InsufficientStockException;
import com.example.challenge.order_service.domain.exception.OrderItemNotFoundException;
import com.example.challenge.order_service.domain.exception.OrderNotFoundException;
import com.example.challenge.order_service.domain.model.Order;
import com.example.challenge.order_service.domain.model.OrderItem;
import com.example.challenge.order_service.domain.model.Product;
import com.example.challenge.order_service.infrastructure.repository.OrderItemRepository;
import com.example.challenge.order_service.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderItemMapper orderItemMapper;
    private final ProductMapper productMapper;

    @Override
    public OrderItemDTO addOrderItem(RequestOrderItemDTO requestOrderItemDTO) {
        Order order = findOrderById(requestOrderItemDTO.getOrderId());
        Product product = findAndMapProduct(requestOrderItemDTO.getProductId());

        OrderItem orderItem = findOrCreateOrderItem(order, product, requestOrderItemDTO);

        return orderItemMapper.toDTO(orderItem);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private Product findAndMapProduct(Long productId) {
        ProductDTO productDTO = productService.findProductById(productId);
        return productMapper.toEntity(productDTO);
    }

    private void validateStockAvailability(Integer quantity, Product product) {
        ProductDTO productDTO = productService.findProductById(product.getId());
        if (productDTO.getStock() < quantity) {
            throw new InsufficientStockException(productDTO.getName());
        }
    }

    private OrderItem findOrCreateOrderItem(Order order, Product product, RequestOrderItemDTO requestOrderItemDTO) {
        return orderItemRepository.findByProductIdAndOrderId(product.getId(), order.getId())
                .map(existingOrderItem -> updateExistingOrderItem(existingOrderItem, product, requestOrderItemDTO))
                .orElseGet(() -> createNewOrderItem(order, product, requestOrderItemDTO));
    }

    private OrderItem updateExistingOrderItem(OrderItem existingOrderItem, Product product, RequestOrderItemDTO requestOrderItemDTO) {
        existingOrderItem.setQuantity(existingOrderItem.getQuantity() + requestOrderItemDTO.getQuantity());
        validateStockAvailability(existingOrderItem.getQuantity(), product);
        existingOrderItem.setPriceAtPurchase(calculateTotalPrice(requestOrderItemDTO.getQuantity(), requestOrderItemDTO.getProductId()));
        return orderItemRepository.save(existingOrderItem);
    }

    private OrderItem createNewOrderItem(Order order, Product product, RequestOrderItemDTO requestOrderItemDTO) {
        validateStockAvailability(requestOrderItemDTO.getQuantity(), product);
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(requestOrderItemDTO.getQuantity())
                .priceAtPurchase(calculateTotalPrice(requestOrderItemDTO.getQuantity(), product.getId()))
                .build();
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = findOrderItemById(id);
        orderItemRepository.delete(orderItem);
    }

    private OrderItem findOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDTO getOrderItem(Long id) {
        OrderItem orderItem = findOrderItemById(id);
        return orderItemMapper.toDTO(orderItem);
    }

    @Override
    public OrderItemDTO updateOrderItem(Long id, RequestUpdateOrderItemDTO requestUpdateOrderItemDTO) {
        OrderItem orderItem = findOrderItemById(id);
        Product product = findAndMapProduct(requestUpdateOrderItemDTO.getProductId());

        validateStockAvailability(requestUpdateOrderItemDTO.getQuantity(), product);

        return orderItemMapper.toDTO(updateOrderItemDetails(orderItem, requestUpdateOrderItemDTO));
    }

    private OrderItem updateOrderItemDetails(OrderItem orderItem, RequestUpdateOrderItemDTO requestUpdateOrderItemDTO) {
        orderItem.setQuantity(requestUpdateOrderItemDTO.getQuantity());
        orderItem.setPriceAtPurchase(calculateTotalPrice(requestUpdateOrderItemDTO.getQuantity(), requestUpdateOrderItemDTO.getProductId()));
        return orderItemRepository.save(orderItem);
    }

    private double calculateTotalPrice(Integer quantity, Long productId) {
        ProductDTO productDTO = productService.findProductById(productId);
        return productDTO.getPrice() * quantity;
    }
}
