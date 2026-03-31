package com.btl.snaker.repository;

import com.btl.snaker.entity.Cart;
import com.btl.snaker.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    CartItem findById(long id);
}
