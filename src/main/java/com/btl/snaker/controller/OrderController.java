package com.btl.snaker.controller;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.OrderRequest;
import com.btl.snaker.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImp orderServiceImp;

    @GetMapping("/admin/get/all")
    public ResponseEntity<?> getAllOrders() {
        ResponseData responseData = new ResponseData();
        responseData.setData(orderServiceImp.getAllOrders());
        responseData.setSuccess(true);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public ResponseEntity<?> getAllOrdersOfUser(@RequestParam long userId) {
        ResponseData responseData = orderServiceImp.getAllOrdersOfUser(userId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/user/insert")
    public ResponseEntity<?> insertOrder(@RequestBody OrderRequest orderRequest) {
        ResponseData responseData = orderServiceImp.insertOrder(orderRequest);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/admin/stats")
    public ResponseEntity<?> getProductSellStats() {
        return new ResponseEntity<>(orderServiceImp.getAllProductSells(),HttpStatus.OK);
    }

    @PostMapping("/admin/solve")
    public ResponseEntity<?> solveOrder(@RequestParam long orderId) {
        ResponseData responseData = orderServiceImp.solveStatusOrder(orderId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
