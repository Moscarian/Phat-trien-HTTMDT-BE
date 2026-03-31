package com.btl.snaker.dto;

import java.util.List;

public class CartDTO {
    private long id;
    private long totalAmount;
    private List<CartItemDTO> cartItemDTOS;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<CartItemDTO> getCartItemDTOS() {
        return cartItemDTOS;
    }

    public void setCartItemDTOS(List<CartItemDTO> cartItemDTOS) {
        this.cartItemDTOS = cartItemDTOS;
    }
}
