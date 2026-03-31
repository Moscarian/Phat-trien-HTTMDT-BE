package com.btl.snaker.repository;

import com.btl.snaker.entity.Cart;
import com.btl.snaker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
