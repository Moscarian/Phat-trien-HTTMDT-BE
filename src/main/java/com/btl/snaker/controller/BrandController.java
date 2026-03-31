package com.btl.snaker.controller;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.service.imp.BrandServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandServiceImp brandServiceImp;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllBrands() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(brandServiceImp.getAllBrands());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(brandServiceImp.getBrandById(id));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/get/name")
    public ResponseEntity<?> getBrandByName(@RequestParam String name) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(brandServiceImp.getBrandByName(name));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
