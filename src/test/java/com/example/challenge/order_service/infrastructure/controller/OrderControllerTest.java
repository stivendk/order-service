package com.example.challenge.order_service.infrastructure.controller;

import com.example.challenge.order_service.application.port.OrderService;
import com.example.challenge.order_service.component.dto.OrderDTO;
import com.example.challenge.order_service.component.dto.RequestOrderDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateOrderDTO;
import com.example.challenge.order_service.component.dto.ResponseDTO;
import com.example.challenge.order_service.domain.enums.OrderStatus;
import com.example.challenge.order_service.infrastructure.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testCreateOrder() throws Exception {
        RequestOrderDTO requestOrderDTO = new RequestOrderDTO();
        OrderDTO createdOrderDTO = new OrderDTO();
        createdOrderDTO.setId(1L);

        when(orderService.createOrder(any(RequestOrderDTO.class))).thenReturn(createdOrderDTO);

        mockMvc.perform(post("/orders")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(requestOrderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.message").value("Order created"));

        verify(orderService, times(1)).createOrder(any(RequestOrderDTO.class));
    }

    @Test
    void testGetAllOrders() throws Exception {
        OrderDTO orderDTO1 = new OrderDTO();
        OrderDTO orderDTO2 = new OrderDTO();
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(orderDTO1, orderDTO2));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.message").value("Order list"));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testGetOrderById() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        when(orderService.getOrderById(anyLong())).thenReturn(orderDTO);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.message").value("Order 1"));

        verify(orderService, times(1)).getOrderById(anyLong());
    }

    @Test
    void testUpdateOrder() throws Exception {
        OrderDTO updatedOrderDTO = new OrderDTO();
        updatedOrderDTO.setId(1L);

        RequestUpdateOrderDTO requestUpdateOrderDTO = new RequestUpdateOrderDTO();
        requestUpdateOrderDTO.setIsPendingPaymentUpdate(true);

        when(orderService.updateOrder(anyLong(), any(RequestUpdateOrderDTO.class)))
                .thenReturn(updatedOrderDTO);

        String requestBody = new ObjectMapper().writeValueAsString(requestUpdateOrderDTO);

        mockMvc.perform(put("/orders/1")
                        .contentType("application/json")
                        .content(requestBody)
                        .param("isPendingPaymentUpdate", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.message").value(Constants.UPDATE_ORDER));

        verify(orderService, times(1)).updateOrder(anyLong(), any(RequestUpdateOrderDTO.class));
    }

    @Test
    void testGetActiveOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setStatus(OrderStatus.NEW_ORDER);
        orderDTO.setItems(List.of());
        orderDTO.setTotalAmount(0.0);
        ResponseDTO<OrderDTO> responseDTO = new ResponseDTO<>(orderDTO, "Active order");
        when(orderService.getActiveOrder()).thenReturn(orderDTO);

        String jsonResponse = new ObjectMapper().writeValueAsString(responseDTO);

        mockMvc.perform(get("/orders/active")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.message").value("Active order"));

        verify(orderService, times(1)).getActiveOrder();
    }

}
