package com.example.challenge.order_service.component.mapper;

import com.example.challenge.order_service.component.dto.OrderDTO;
import com.example.challenge.order_service.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdAt", source = "createdAt")
    OrderDTO toDTO(Order order);

    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdAt", source = "createdAt")
    Order toEntity(OrderDTO orderDTO);
}
