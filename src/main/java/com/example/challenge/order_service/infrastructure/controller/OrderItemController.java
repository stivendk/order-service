package com.example.challenge.order_service.infrastructure.controller;

import com.example.challenge.order_service.application.port.OrderItemService;
import com.example.challenge.order_service.component.dto.OrderItemDTO;
import com.example.challenge.order_service.component.dto.RequestOrderItemDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateOrderItemDTO;
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
@RequestMapping("order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Operation(summary = "Get all order items", description = "Retrieve a list of all order items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order items retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": [{ \"id\": 1, \"quantity\": 2, \"priceAtPurchase\": 99.99, \"product\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop with 16GB RAM\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/image.jpg\", \"status\": \"AVAILABLE\" } }], \"message\": \"Order items list\" }"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Internal server error\" }")))
    })
    @GetMapping
    public ResponseEntity<ResponseDTO<List<OrderItemDTO>>> getOrderItems() {
        return ResponseEntity.ok()
                .body(new ResponseDTO<>(
                        orderItemService.getAllOrderItems(),
                        "Order items list"));
    }

    @Operation(summary = "Get order item by ID", description = "Retrieve a specific order item by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order item retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 1, \"quantity\": 2, \"priceAtPurchase\": 99.99, \"product\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop with 16GB RAM\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/image.jpg\", \"status\": \"AVAILABLE\" } }, \"message\": \"Order item 1\" }"))),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Order item not found\" }")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderItemDTO>> getOrderItemById(
            @Parameter(description = "ID of the order item to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok()
                .body(new ResponseDTO<>(
                        orderItemService.getOrderItem(id),
                        "Order item " + id));
    }

    @Operation(summary = "Add a new order item", description = "Add a new item to an existing order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order item added successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 2, \"quantity\": 3, \"priceAtPurchase\": 79.99, \"product\": { \"id\": 2, \"name\": \"Mouse\", \"description\": \"Wireless mouse with ergonomic design\", \"price\": 49.99, \"stock\": 100, \"imageUrl\": \"http://example.com/mouse.jpg\", \"status\": \"AVAILABLE\" } }, \"message\": \"Item added successfully\" }"))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Invalid input\" }")))
    })
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO<OrderItemDTO>> addOrderItem(
            @RequestBody RequestOrderItemDTO requestOrderItemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO<>(
                        orderItemService.addOrderItem(requestOrderItemDTO),
                        "Item added successfully"));
    }

    @Operation(summary = "Update an order item", description = "Update the details of an order item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order item updated successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 1, \"quantity\": 4, \"priceAtPurchase\": 89.99, \"product\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop with 16GB RAM\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/image.jpg\", \"status\": \"AVAILABLE\" } }, \"message\": \"Order item updated\" }"))),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Order item not found\" }")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderItemDTO>> updateOrderItem(
            @Parameter(description = "ID of the order item to update", required = true)
            @PathVariable Long id,
            @RequestBody RequestUpdateOrderItemDTO requestUpdateOrderItemDTO) {
        return ResponseEntity.ok()
                .body(new ResponseDTO<>(
                        orderItemService.updateOrderItem(id, requestUpdateOrderItemDTO), ""));
    }

    @Operation(summary = "Delete an order item", description = "Remove an item from an order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order item deleted successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Item removed\" }"))),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Order item not found\" }")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteOrderItem(
            @Parameter(description = "ID of the order item to delete", required = true)
            @PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok().body(new ResponseDTO<>(null, "Item removed"));
    }
}