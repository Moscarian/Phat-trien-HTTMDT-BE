package com.btl.snaker.entity;

import com.btl.snaker.entity.key.KeyOrderItem;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "order_item")
public class OrderItem {
    @EmbeddedId
    private KeyOrderItem keyOrderItem;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Long price;
    @Column(name = "size")
    private Integer size;
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "order_id",insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product;

    public KeyOrderItem getKeyOrderItem() {
        return keyOrderItem;
    }

    public void setKeyOrderItem(KeyOrderItem keyOrderItem) {
        this.keyOrderItem = keyOrderItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
