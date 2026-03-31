package com.btl.snaker.repository;

import com.btl.snaker.entity.Order;
import com.btl.snaker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    Order findById(long id);
}
