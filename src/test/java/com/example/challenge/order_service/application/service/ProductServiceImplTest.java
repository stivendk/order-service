package com.example.challenge.order_service.application.service;

import com.example.challenge.order_service.component.dto.ProductDTO;
import com.example.challenge.order_service.component.dto.RequestProductDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateProductDTO;
import com.example.challenge.order_service.component.mapper.ProductMapper;
import com.example.challenge.order_service.domain.exception.ProductNotFoundException;
import com.example.challenge.order_service.domain.model.Product;
import com.example.challenge.order_service.domain.enums.ProductStatus;
import com.example.challenge.order_service.infrastructure.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.findProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).toDTO(product);
    }

    @Test
    void testFindProductById_ProductNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testCreateProduct() {
        // Datos de entrada
        RequestProductDTO requestProductDTO = new RequestProductDTO();
        requestProductDTO.setDescription("Test Description");
        requestProductDTO.setName("Test Name");
        requestProductDTO.setPrice(100.0);
        requestProductDTO.setImageUrl("test_image_url");
        requestProductDTO.setStock(10);

        // Mapeo de ProductDTO a Product (el que ahora usará el servicio internamente)
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription(requestProductDTO.getDescription());
        productDTO.setName(requestProductDTO.getName());
        productDTO.setPrice(requestProductDTO.getPrice());
        productDTO.setImageUrl(requestProductDTO.getImageUrl());
        productDTO.setStock(requestProductDTO.getStock());

        // Producto a guardar
        Product product = new Product();
        Product savedProduct = new Product();
        savedProduct.setId(1L);

        // El producto devuelto tras guardarse
        ProductDTO savedProductDTO = new ProductDTO();
        savedProductDTO.setId(1L);

        // Mock del comportamiento
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toDTO(savedProduct)).thenReturn(savedProductDTO);

        // Ejecución del método
        ProductDTO result = productService.createProduct(requestProductDTO);

        // Verificaciones
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productMapper, times(1)).toEntity(productDTO);
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toDTO(savedProduct);
    }


    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        RequestUpdateProductDTO requestDTO = new RequestUpdateProductDTO();
        requestDTO.setName("Updated Name");
        requestDTO.setDescription("Updated Description");
        requestDTO.setPrice(150.0);
        requestDTO.setStock(20);
        requestDTO.setImageUrl("updated_image_url");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setStock(10); // Existing stock

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated Name");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(150.0);
        updatedProduct.setStock(20);
        updatedProduct.setStatus(ProductStatus.AVAILABLE);
        updatedProduct.setImageUrl("updated_image_url");

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setId(productId);
        updatedProductDTO.setName("Updated Name");
        updatedProductDTO.setDescription("Updated Description");
        updatedProductDTO.setPrice(150.0);
        updatedProductDTO.setStock(20);
        updatedProductDTO.setStatus(ProductStatus.AVAILABLE);
        updatedProductDTO.setImageUrl("updated_image_url");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productMapper.toDTO(updatedProduct)).thenReturn(updatedProductDTO);

        ProductDTO result = productService.updateProduct(productId, requestDTO);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(150.0, result.getPrice());
        assertEquals(20, result.getStock());
        assertEquals("updated_image_url", result.getImageUrl());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
        verify(productMapper, times(1)).toDTO(updatedProduct);
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        Long productId = 1L;
        RequestUpdateProductDTO requestDTO = new RequestUpdateProductDTO();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, requestDTO));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testUpdateStockProduct() {
        Long productId = 1L;
        Integer newStock = 5;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setStock(10); // Existing stock

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setStock(newStock);
        updatedProduct.setStatus(ProductStatus.AVAILABLE);

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setId(productId);
        updatedProductDTO.setStock(newStock);
        updatedProductDTO.setStatus(ProductStatus.AVAILABLE);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productMapper.toDTO(updatedProduct)).thenReturn(updatedProductDTO);

        ProductDTO result = productService.updateStockProduct(productId, newStock);

        assertNotNull(result);
        assertEquals(newStock, result.getStock());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
        verify(productMapper, times(1)).toDTO(updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setPrice(100.0);
        product1.setStock(10);

        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(1L);
        productDTO1.setPrice(100.0);
        productDTO1.setStock(10);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setPrice(90.0);
        product2.setStock(10);

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setId(2L);
        productDTO2.setPrice(90.0);
        productDTO2.setStock(10);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toDTO(product1)).thenReturn(productDTO1);
        when(productMapper.toDTO(product2)).thenReturn(productDTO2);

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(productDTO1));
        assertTrue(result.contains(productDTO2));
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toDTO(product1);
        verify(productMapper, times(1)).toDTO(product2);
    }
}
