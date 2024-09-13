package com.example.challenge.order_service.application.port;

import com.example.challenge.order_service.component.dto.OrderItemDTO;
import com.example.challenge.order_service.component.dto.RequestOrderItemDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateOrderItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {

    OrderItemDTO addOrderItem(RequestOrderItemDTO requestOrderItemDTO);

    void deleteOrderItem(Long id);

    List<OrderItemDTO> getAllOrderItems();

    OrderItemDTO getOrderItem(Long id);

    OrderItemDTO updateOrderItem(Long id, RequestUpdateOrderItemDTO requestUpdateOrderItemDTO);
}
