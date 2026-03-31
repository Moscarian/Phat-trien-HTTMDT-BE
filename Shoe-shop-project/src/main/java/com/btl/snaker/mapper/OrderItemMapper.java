package com.btl.snaker.mapper;

import com.btl.snaker.dto.OrderItemDTO;
import com.btl.snaker.entity.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setProductName(orderItem.getProduct().getName());
        orderItemDTO.setProductDescription(orderItem.getProduct().getDescription());
        orderItemDTO.setProductImage(orderItem.getProduct().getImage());
        orderItemDTO.setProductBrand(orderItem.getProduct().getBrand().getName());
        orderItemDTO.setProductCategory(orderItem.getProduct().getCategory().getName());
        orderItemDTO.setProductPrice(orderItem.getProduct().getPrice());
        orderItemDTO.setProductQuantity(orderItem.getQuantity());
        orderItemDTO.setProductSize(orderItem.getSize());
        orderItemDTO.setTotalAmount(orderItem.getPrice() * orderItem.getQuantity());
        return orderItemDTO;
    }
}
