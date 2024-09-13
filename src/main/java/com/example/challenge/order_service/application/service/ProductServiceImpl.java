package com.example.challenge.order_service.application.service;

import com.example.challenge.order_service.application.port.ProductService;
import com.example.challenge.order_service.component.dto.ProductDTO;
import com.example.challenge.order_service.component.dto.RequestProductDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateProductDTO;
import com.example.challenge.order_service.component.mapper.ProductMapper;
import com.example.challenge.order_service.domain.enums.ProductStatus;
import com.example.challenge.order_service.domain.exception.ProductNotFoundException;
import com.example.challenge.order_service.domain.model.Product;
import com.example.challenge.order_service.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO findProductById(Long id) {
        Product product = findProduct(id);
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(RequestProductDTO requestProductDTO) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription(requestProductDTO.getDescription());
        productDTO.setName(requestProductDTO.getName());
        productDTO.setPrice(requestProductDTO.getPrice());
        productDTO.setImageUrl(requestProductDTO.getImageUrl());
        productDTO.setStock(requestProductDTO.getStock());
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, RequestUpdateProductDTO requestUpdateProductDTO) {
        Product product = findProduct(id);
        product.setName(requestUpdateProductDTO.getName());
        product.setDescription(requestUpdateProductDTO.getDescription());
        product.setPrice(requestUpdateProductDTO.getPrice());
        product.setStatus(isProductAvailable(product));
        product.setImageUrl(requestUpdateProductDTO.getImageUrl());
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateStockProduct(Long id, Integer stock) {
        Product product = findProduct(id);
        product.setStock(stock);
        product.setStatus(isProductAvailable(product));
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Product findProduct(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductStatus isProductAvailable(Product product) {
        if(product.getStock() <= 0){
            return ProductStatus.UNAVAILABLE;
        }
        return ProductStatus.AVAILABLE;
    }
}
