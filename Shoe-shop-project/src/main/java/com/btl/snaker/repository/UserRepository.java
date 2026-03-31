package com.btl.snaker.repository;

import com.btl.snaker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPhoneAndPassword(String username, String password);
    User findByPhone(String phone);
    User findByEmail(String email);
    User findById(long id);
}
