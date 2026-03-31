package com.btl.snaker.controller;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllCategories() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(categoryServiceImp.getAllCategories());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable long id) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(categoryServiceImp.getCategoryById(id));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
