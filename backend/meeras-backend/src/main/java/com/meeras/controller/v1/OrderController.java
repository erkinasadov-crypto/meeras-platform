package com.meeras.controller.v1;

import com.meeras.dto.OrderItemRequest;
import com.meeras.dto.OrderResponse;
import com.meeras.dto.OrderRequest;
import com.meeras.dto.OrderItemResponse;
import com.meeras.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest req) {
        OrderResponse created = orderService.createOrder(req);
        return ResponseEntity.created(URI.create("/api/v1/orders/" + created.getId())).body(created);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<OrderItemResponse> addItem(@PathVariable("id") UUID id, @RequestBody OrderItemRequest req) {
        OrderItemResponse item = orderService.addItem(id, req);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable("id") UUID id) {
        OrderResponse r = orderService.getOrder(id);
        return ResponseEntity.ok(r);
    }

}
