package com.example.challenge.order_service.infrastructure.controller;

import com.example.challenge.order_service.application.port.ProductService;
import com.example.challenge.order_service.component.dto.ProductDTO;
import com.example.challenge.order_service.component.dto.RequestProductDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateProductDTO;
import com.example.challenge.order_service.component.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/laptop.jpg\", \"status\": \"AVAILABLE\" }, \"message\": \"Product 1\" }"))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Product not found\" }")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> getProduct(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO<>(
                productService.findProductById(id),
                "Product " + id));
    }

    @Operation(summary = "Get all products", description = "Retrieve a list of all products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": [{ \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/laptop.jpg\", \"status\": \"AVAILABLE\" }, { \"id\": 2, \"name\": \"Mouse\", \"description\": \"Wireless mouse\", \"price\": 49.99, \"stock\": 100, \"imageUrl\": \"http://example.com/mouse.jpg\", \"status\": \"AVAILABLE\" }], \"message\": \"Product list\" }"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Internal server error\" }")))
    })
    @GetMapping
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getProducts() {
        return ResponseEntity.ok()
                .body(new ResponseDTO<>(
                        productService.getAllProducts(),
                        "Product list"));
    }

    @Operation(summary = "Add a new product", description = "Create a new product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 3, \"name\": \"Keyboard\", \"description\": \"Mechanical keyboard\", \"price\": 89.99, \"stock\": 75, \"imageUrl\": \"http://example.com/keyboard.jpg\", \"status\": \"AVAILABLE\" }, \"message\": \"A new product has been created\" }"))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Invalid input\" }")))
    })
    @PostMapping
    public ResponseEntity<ResponseDTO<ProductDTO>> addProduct(
            @RequestBody RequestProductDTO requestProductDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO<>(
                        productService.createProduct(requestProductDTO),
                        "A new product has been created"));
    }

    @Operation(summary = "Update a product", description = "Update the details of an existing product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"Updated description\", \"price\": 899.99, \"stock\": 60, \"imageUrl\": \"http://example.com/laptop-updated.jpg\", \"status\": \"AVAILABLE\" }, \"message\": \"Product has been updated\" }"))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Product not found\" }")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable Long id,
            @RequestBody RequestUpdateProductDTO requestUpdateProductDTO) {
        return ResponseEntity.ok(new ResponseDTO<>(
                productService.updateProduct(id, requestUpdateProductDTO), "Product has been updated"
        ));
    }
}