package com.meeras.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "sku_code")
    private String skuCode;

    private String title;

    @Column(name = "unit_price", precision = 12, scale = 2)
    private BigDecimal unitPrice;

    private Integer qty;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "options_json", columnDefinition = "jsonb")
    private Map<String, Object> optionsJson;

    @Column(name = "line_total", precision = 12, scale = 2)
    private BigDecimal lineTotal;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (lineTotal == null && unitPrice != null && qty != null) {
            lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Map<String, Object> getOptionsJson() {
        return optionsJson;
    }

    public void setOptionsJson(Map<String, Object> optionsJson) {
        this.optionsJson = optionsJson;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }
}
