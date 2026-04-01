package com.btl.snaker.controller;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private VNPayService vnPayService;

    // Frontend gọi API này để lấy URL thanh toán VNPay
    @PostMapping("/vnpay/create")
    public ResponseEntity<?> createPayment(
            @RequestParam long amount,
            @RequestParam String orderInfo,
            HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            String ipAddr = request.getRemoteAddr();
            String paymentUrl = vnPayService.createPaymentUrl(amount, orderInfo, ipAddr);
            responseData.setSuccess(true);
            responseData.setData(paymentUrl);
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("Tạo URL thanh toán thất bại: " + e.getMessage());
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // VNPay redirect về URL này sau khi thanh toán
    @GetMapping("/vnpay/return")
    public ResponseEntity<?> paymentReturn(@RequestParam Map<String, String> params) {
        ResponseData responseData = new ResponseData();
        try {
            boolean isValid = vnPayService.verifyPayment(params);
            if (isValid && "00".equals(params.get("vnp_ResponseCode"))) {
                responseData.setSuccess(true);
                responseData.setDescription("Thanh toán thành công");
            } else {
                responseData.setSuccess(false);
                responseData.setDescription("Thanh toán thất bại hoặc bị hủy");
            }
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("Lỗi xác thực: " + e.getMessage());
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
