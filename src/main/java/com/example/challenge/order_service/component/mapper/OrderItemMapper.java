package com.example.challenge.order_service.component.mapper;

import com.example.challenge.order_service.component.dto.OrderItemDTO;
import com.example.challenge.order_service.domain.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDTO toDTO(OrderItem orderItem);

    OrderItem toEntity(OrderItemDTO orderItemDTO);
}
