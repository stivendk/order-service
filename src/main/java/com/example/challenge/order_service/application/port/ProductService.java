package com.example.challenge.order_service.application.port;

import com.example.challenge.order_service.component.dto.ProductDTO;
import com.example.challenge.order_service.component.dto.RequestProductDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductDTO findProductById(Long id);

    ProductDTO createProduct(RequestProductDTO requestProductDTO);

    ProductDTO updateProduct(Long id, RequestUpdateProductDTO requestUpdateProductDTO);

    ProductDTO updateStockProduct(Long id, Integer stock);

    void deleteProduct(Long id);

    List<ProductDTO> getAllProducts();
}
