package com.meeras.service;

import com.meeras.dto.OrderItemRequest;
import com.meeras.dto.OrderItemResponse;
import com.meeras.dto.OrderRequest;
import com.meeras.dto.OrderResponse;
import com.meeras.model.Order;
import com.meeras.model.OrderItem;
import com.meeras.repository.OrderItemRepository;
import com.meeras.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest req) {
        Order order = new Order();
        order.setType(req.getType());
        order.setStatus("CREATED");
        order = orderRepository.save(order);
        return toResponse(order);
    }

    @Transactional
    public OrderItemResponse addItem(UUID orderId, OrderItemRequest req) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        OrderItem item = new OrderItem();
        item.setItemType(req.getItemType());
        item.setSkuCode(req.getSkuCode());
        item.setTitle(req.getTitle());
        item.setUnitPrice(req.getUnitPrice());
        item.setQty(req.getQty() == null ? 1 : req.getQty());
        item.setOptionsJson(req.getOptions());
        if (req.getUnitPrice() != null && item.getQty() != null) {
            item.setLineTotal(req.getUnitPrice().multiply(BigDecimal.valueOf(item.getQty())));
        }
        item.setOrder(order);
        item = itemRepository.save(item);
        order.getItems().add(item);
        orderRepository.save(order);
        return toItemResponse(item);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse r = new OrderResponse();
        r.setId(order.getId());
        r.setType(order.getType());
        r.setStatus(order.getStatus());
        r.setCreatedAt(order.getCreatedAt());
        List<OrderItemResponse> items = order.getItems().stream().map(this::toItemResponse).collect(Collectors.toList());
        r.setItems(items);

        BigDecimal services = items.stream()
                .filter(i -> i.getItemType() != null && i.getItemType().equalsIgnoreCase("SERVICE"))
                .map(i -> i.getLineTotal() == null ? BigDecimal.ZERO : i.getLineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal flowers = items.stream()
                .filter(i -> i.getItemType() != null && i.getItemType().equalsIgnoreCase("FLOWER"))
                .map(i -> i.getLineTotal() == null ? BigDecimal.ZERO : i.getLineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        r.setServicesSubtotal(services);
        r.setFlowersSubtotal(flowers);
        r.setTotal(services.add(flowers));
        return r;
    }

    private OrderItemResponse toItemResponse(OrderItem it) {
        OrderItemResponse r = new OrderItemResponse();
        r.setId(it.getId());
        r.setItemType(it.getItemType());
        r.setSkuCode(it.getSkuCode());
        r.setTitle(it.getTitle());
        r.setUnitPrice(it.getUnitPrice());
        r.setQty(it.getQty());
        r.setOptions(it.getOptionsJson());
        r.setLineTotal(it.getLineTotal());
        return r;
    }
}
