package com.example.challenge.order_service.infrastructure.controller;

import com.example.challenge.order_service.application.port.OrderService;
import com.example.challenge.order_service.component.dto.OrderDTO;
import com.example.challenge.order_service.component.dto.RequestOrderDTO;
import com.example.challenge.order_service.component.dto.RequestUpdateOrderDTO;
import com.example.challenge.order_service.component.dto.ResponseDTO;
import com.example.challenge.order_service.infrastructure.constants.Constants;
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
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order", description = "Creates a new order with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 123, \"totalAmount\": 250.75, \"status\": \"NEW_ORDER\", \"items\": [{ \"id\": 1, \"quantity\": 2, \"priceAtPurchase\": 99.99, \"product\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop with 16GB RAM\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/image.jpg\", \"status\": \"AVAILABLE\" } }], \"createdAt\": \"2024-09-12T14:30:00\", \"updatedAt\": \"2024-09-13T10:15:00\" }, \"message\": \"Order created\" }"))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Invalid input\" }")))
    })
    @PostMapping
    public ResponseEntity<ResponseDTO<OrderDTO>> createOrder(
            @Parameter(description = "Order details for the new order", required = true)
            @RequestBody RequestOrderDTO requestOrderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO<>(
                        orderService.createOrder(requestOrderDTO),
                        "Order created"));
    }

    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order list retrieved",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": [{ \"id\": 123, \"totalAmount\": 250.75, \"status\": \"NEW_ORDER\", \"items\": [{ \"id\": 1, \"quantity\": 2, \"priceAtPurchase\": 99.99, \"product\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop with 16GB RAM\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/image.jpg\", \"status\": \"AVAILABLE\" } }], \"createdAt\": \"2024-09-12T14:30:00\", \"updatedAt\": \"2024-09-13T10:15:00\" }], \"message\": \"Order list\" }"))),
            @ApiResponse(responseCode = "404", description = "Orders not found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": [], \"message\": \"Orders not found\" }")))
    })
    @GetMapping()
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> getAllOrders() {
        return ResponseEntity.ok()
                .body(new ResponseDTO<>(
                        orderService.getAllOrders(),
                        "Order list"));
    }

    @Operation(summary = "Get order by ID", description = "Retrieves a specific order by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 123, \"totalAmount\": 250.75, \"status\": \"NEW_ORDER\", \"items\": [{ \"id\": 1, \"quantity\": 2, \"priceAtPurchase\": 99.99, \"product\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop with 16GB RAM\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/image.jpg\", \"status\": \"AVAILABLE\" } }], \"createdAt\": \"2024-09-12T14:30:00\", \"updatedAt\": \"2024-09-13T10:15:00\" }, \"message\": \"Order 123\" }"))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Order not found\" }")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderDTO>> getOrderById(
            @Parameter(description = "ID of the order to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO<>(
                orderService.getOrderById(id),
                "Order " + id));
    }

    @Operation(summary = "Update order", description = "Updates an order by its ID, marking it as pending payment or paid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 123, \"totalAmount\": 250.75, \"status\": \"PENDING_PAYMENT\", \"items\": [{ \"id\": 1, \"quantity\": 2, \"priceAtPurchase\": 99.99, \"product\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop with 16GB RAM\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/image.jpg\", \"status\": \"AVAILABLE\" } }], \"createdAt\": \"2024-09-12T14:30:00\", \"updatedAt\": \"2024-09-13T10:15:00\" }, \"message\": \"Order updated\" }"))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Order not found\" }")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderDTO>> updateOrder(
            @Parameter(description = "ID of the order to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Flag to mark if the order is pending payment", required = true)
            @RequestBody RequestUpdateOrderDTO requestUpdateOrderDTO) {
        return ResponseEntity.ok(new ResponseDTO<>(
                orderService.updateOrder(id, requestUpdateOrderDTO),
                requestUpdateOrderDTO.getIsPendingPaymentUpdate() ? Constants.UPDATE_ORDER : Constants.PAID_ORDER));
    }

    @Operation(summary = "Get active order", description = "Retrieves a specific order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": { \"id\": 123, \"totalAmount\": 250.75, \"status\": \"NEW_ORDER\", \"items\": [{ \"id\": 1, \"quantity\": 2, \"priceAtPurchase\": 99.99, \"product\": { \"id\": 1, \"name\": \"Laptop\", \"description\": \"High-performance laptop with 16GB RAM\", \"price\": 999.99, \"stock\": 50, \"imageUrl\": \"http://example.com/image.jpg\", \"status\": \"AVAILABLE\" } }], \"createdAt\": \"2024-09-12T14:30:00\", \"updatedAt\": \"2024-09-13T10:15:00\" }, \"message\": \"Order 123\" }"))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class),
                            examples = @ExampleObject(value = "{ \"data\": null, \"message\": \"Order not found\" }")))
    })
    @GetMapping("/active")
    public ResponseEntity<ResponseDTO<OrderDTO>> getActiveOrder() {
        return ResponseEntity.ok(new ResponseDTO<>(
                orderService.getActiveOrder(),
                "Active order"));
    }
}
