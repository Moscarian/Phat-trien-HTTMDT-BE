package com.btl.snaker.repository;

import com.btl.snaker.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Voucher findByCode(String code);
    List<Voucher> findByActiveTrue();
}
