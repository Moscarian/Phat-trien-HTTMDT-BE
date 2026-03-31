package com.btl.snaker.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class KeyOrderItem {
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_id")
    private Long productId;

    public KeyOrderItem(){
    }

    public KeyOrderItem(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
