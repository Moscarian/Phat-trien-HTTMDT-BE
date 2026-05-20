package com.btl.snaker.controller;

import com.btl.snaker.entity.Voucher;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherRepository voucherRepository;

    // Khách hàng lấy danh sách tất cả voucher còn hiệu lực
    @GetMapping("/user/available")
    public ResponseEntity<?> getAvailableVouchers(@RequestParam long orderAmount) {
        ResponseData responseData = new ResponseData();
        Date now = new Date();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Voucher v : voucherRepository.findByActiveTrue()) {
            boolean notExpired = v.getEndDate() == null || v.getEndDate().after(now);
            boolean notStarted = v.getStartDate() != null && v.getStartDate().after(now);
            boolean hasUsage = v.getUsageLimit() == null || v.getUsedCount() < v.getUsageLimit();
            boolean meetsMinOrder = v.getMinOrderAmount() == null || orderAmount >= v.getMinOrderAmount();

            if (notExpired && !notStarted && hasUsage) {
                // Tính số tiền giảm
                long discount = 0;
                if ("PERCENT".equals(v.getDiscountType())) {
                    discount = orderAmount * v.getDiscountValue() / 100;
                    if (v.getMaxDiscount() != null && discount > v.getMaxDiscount()) {
                        discount = v.getMaxDiscount();
                    }
                } else {
                    discount = v.getDiscountValue();
                }

                Map<String, Object> map = new HashMap<>();
                map.put("id", v.getId());
                map.put("code", v.getCode());
                map.put("description", v.getDescription());
                map.put("discountType", v.getDiscountType());
                map.put("discountValue", v.getDiscountValue());
                map.put("maxDiscount", v.getMaxDiscount());
                map.put("minOrderAmount", v.getMinOrderAmount());
                map.put("endDate", v.getEndDate());
                map.put("discountAmount", discount);
                map.put("canUse", meetsMinOrder); // true = dùng được, false = chưa đủ điều kiện
                result.add(map);
            }
        }
        responseData.setSuccess(true);
        responseData.setData(result);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Admin lấy tất cả voucher
    @GetMapping("/admin/get/all")
    public ResponseEntity<?> getAllVouchers() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(voucherRepository.findAll());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Admin tạo voucher
    @PostMapping("/admin/create")
    public ResponseEntity<?> createVoucher(@RequestBody Voucher voucher) {
        ResponseData responseData = new ResponseData();
        try {
            if (voucher.getUsedCount() == null) voucher.setUsedCount(0);
            if (voucher.getActive() == null) voucher.setActive(true);
            voucherRepository.save(voucher);
            responseData.setSuccess(true);
            responseData.setDescription("Tạo voucher thành công");
            responseData.setData(voucher);
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("Lỗi: " + e.getMessage());
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Admin cập nhật voucher
    @PostMapping("/admin/update")
    public ResponseEntity<?> updateVoucher(@RequestBody Voucher voucher) {
        ResponseData responseData = new ResponseData();
        try {
            voucherRepository.save(voucher);
            responseData.setSuccess(true);
            responseData.setDescription("Cập nhật voucher thành công");
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("Lỗi: " + e.getMessage());
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Admin xóa voucher
    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> deleteVoucher(@RequestParam long id) {
        ResponseData responseData = new ResponseData();
        voucherRepository.deleteById(id);
        responseData.setSuccess(true);
        responseData.setDescription("Xóa voucher thành công");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
