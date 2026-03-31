package com.btl.snaker.repository;

import com.btl.snaker.entity.Product;
import com.btl.snaker.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    ProductSize findByProductAndSize(Product product, Integer size);
}
