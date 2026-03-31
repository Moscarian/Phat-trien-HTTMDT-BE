package com.btl.snaker.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class KeyHistoryItem {
    @Column(name = "history_id")
    private Long historyId;
    @Column(name = "product_id")
    private Long productId;

    public KeyHistoryItem() {
    }

    public KeyHistoryItem(Long historyId, Long productId) {
        this.historyId = historyId;
        this.productId = productId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
