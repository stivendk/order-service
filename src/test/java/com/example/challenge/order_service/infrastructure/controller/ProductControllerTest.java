package com.example.challenge.order_service.infrastructure.controller;

import com.example.challenge.order_service.application.port.ProductService;
import com.example.challenge.order_service.component.dto.ProductDTO;
import com.example.challenge.order_service.component.dto.RequestProductDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetProduct() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        when(productService.findProductById(productId)).thenReturn(productDTO);

        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productId))
                .andExpect(jsonPath("$.message").value("Product " + productId));

        verify(productService, times(1)).findProductById(productId);
    }

    @Test
    void testGetProducts() throws Exception {
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(1L);

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setId(2L);

        when(productService.getAllProducts()).thenReturn(Arrays.asList(productDTO1, productDTO2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.message").value("Product list"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testAddProduct() throws Exception {
        RequestProductDTO requestProductDTO = new RequestProductDTO();
        requestProductDTO.setDescription("Test Description");
        requestProductDTO.setName("Test Name");
        requestProductDTO.setPrice(100.0);
        requestProductDTO.setImageUrl("test_image_url");
        requestProductDTO.setStock(10);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setDescription(requestProductDTO.getDescription());
        productDTO.setName(requestProductDTO.getName());
        productDTO.setPrice(requestProductDTO.getPrice());
        productDTO.setImageUrl(requestProductDTO.getImageUrl());
        productDTO.setStock(requestProductDTO.getStock());

        when(productService.createProduct(requestProductDTO)).thenReturn(productDTO);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(requestProductDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.description").value(requestProductDTO.getDescription()))
                .andExpect(jsonPath("$.data.name").value(requestProductDTO.getName()))
                .andExpect(jsonPath("$.data.price").value(requestProductDTO.getPrice()))
                .andExpect(jsonPath("$.data.imageUrl").value(requestProductDTO.getImageUrl()))
                .andExpect(jsonPath("$.data.stock").value(requestProductDTO.getStock()))
                .andExpect(jsonPath("$.message").value("A new product has been created"));

        verify(productService, times(1)).createProduct(requestProductDTO);
    }


    @Test
    void testUpdateProduct() throws Exception {
        Long productId = 1L;
        RequestUpdateProductDTO requestUpdateProductDTO = new RequestUpdateProductDTO();
        requestUpdateProductDTO.setName("Updated Name");
        requestUpdateProductDTO.setDescription("Updated Description");
        requestUpdateProductDTO.setPrice(200.0);
        requestUpdateProductDTO.setStock(15);
        requestUpdateProductDTO.setImageUrl("http://example.com/image.jpg");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("Updated Name");
        productDTO.setDescription("Updated Description");
        productDTO.setPrice(200.0);
        productDTO.setStock(15);
        productDTO.setImageUrl("http://example.com/image.jpg");

        when(productService.updateProduct(eq(productId), any(RequestUpdateProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(requestUpdateProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productId))
                .andExpect(jsonPath("$.message").value("Product has been updated"));

        verify(productService, times(1)).updateProduct(eq(productId), any(RequestUpdateProductDTO.class));
    }

}
