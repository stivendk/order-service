package com.example.challenge.order_service.infrastructure.controller;

import com.example.challenge.order_service.application.port.OrderItemService;
import com.example.challenge.order_service.component.dto.OrderItemDTO;
import com.example.challenge.order_service.component.dto.RequestOrderItemDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateOrderItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderItemControllerTest {

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderItemController orderItemController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderItemController).build();
    }

    @Test
    void testGetOrderItems() throws Exception {
        OrderItemDTO orderItemDTO1 = new OrderItemDTO();
        orderItemDTO1.setId(1L);
        OrderItemDTO orderItemDTO2 = new OrderItemDTO();
        orderItemDTO2.setId(2L);

        when(orderItemService.getAllOrderItems()).thenReturn(Arrays.asList(orderItemDTO1, orderItemDTO2));

        mockMvc.perform(get("/order-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.message").value("Order items list"));

        verify(orderItemService, times(1)).getAllOrderItems();
    }

    @Test
    void testGetOrderItemById() throws Exception {
        Long orderItemId = 1L;
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItemId);

        when(orderItemService.getOrderItem(orderItemId)).thenReturn(orderItemDTO);

        mockMvc.perform(get("/order-items/{id}", orderItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(orderItemId))
                .andExpect(jsonPath("$.message").value("Order item " + orderItemId));

        verify(orderItemService, times(1)).getOrderItem(orderItemId);
    }

    @Test
    void testAddOrderItem() throws Exception {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(1L);
        RequestOrderItemDTO requestOrderItemDTO = new RequestOrderItemDTO();

        when(orderItemService.addOrderItem(any(RequestOrderItemDTO.class))).thenReturn(orderItemDTO);

        mockMvc.perform(post("/order-items/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestOrderItemDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.message").value("Item added successfully"));

        verify(orderItemService, times(1)).addOrderItem(any(RequestOrderItemDTO.class));
    }

    @Test
    void testUpdateOrderItem() throws Exception {
        Long orderItemId = 1L;
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItemId);
        RequestUpdateOrderItemDTO requestOrderItemDTO = new RequestUpdateOrderItemDTO();

        when(orderItemService.updateOrderItem(eq(orderItemId), any(RequestUpdateOrderItemDTO.class)))
                .thenReturn(orderItemDTO);

        mockMvc.perform(put("/order-items/{id}", orderItemId)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(requestOrderItemDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(orderItemId))
                .andExpect(jsonPath("$.message").value(""));

        verify(orderItemService, times(1)).updateOrderItem(eq(orderItemId), any(RequestUpdateOrderItemDTO.class));
    }

    @Test
    void testDeleteOrderItem() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(delete("/order-items")
                        .param("orderId", orderId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value((Object) null))
                .andExpect(jsonPath("$.message").value("Item removed"));

        verify(orderItemService, times(1)).deleteOrderItem(orderId);
    }
}
