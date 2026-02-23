package com.meeras.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderResponse {
    private UUID id;
    private String type;
    private String status;
    private Instant createdAt;
    private List<OrderItemResponse> items = new ArrayList<>();
    private BigDecimal servicesSubtotal = BigDecimal.ZERO;
    private BigDecimal flowersSubtotal = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public BigDecimal getServicesSubtotal() {
        return servicesSubtotal;
    }

    public void setServicesSubtotal(BigDecimal servicesSubtotal) {
        this.servicesSubtotal = servicesSubtotal;
    }

    public BigDecimal getFlowersSubtotal() {
        return flowersSubtotal;
    }

    public void setFlowersSubtotal(BigDecimal flowersSubtotal) {
        this.flowersSubtotal = flowersSubtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
