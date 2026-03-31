package com.btl.snaker.repository;

import com.btl.snaker.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findById(long id);
    Brand findByName(String name);
}
