package com.example.challenge.order_service.application.port;

import com.example.challenge.order_service.component.dto.OrderDTO;
import com.example.challenge.order_service.component.dto.RequestOrderDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateOrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDTO createOrder(RequestOrderDTO requestOrderDTO);
    OrderDTO updateOrder(Long id, RequestUpdateOrderDTO requestUpdateOrderDTO);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long id);
    OrderDTO getActiveOrder();
}
