package com.btl.snaker.repository;

import com.btl.snaker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);
    Category findByName(String name);
}
