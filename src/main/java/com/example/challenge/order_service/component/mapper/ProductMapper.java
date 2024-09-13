package com.example.challenge.order_service.component.mapper;

import com.example.challenge.order_service.component.dto.ProductDTO;
import com.example.challenge.order_service.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
