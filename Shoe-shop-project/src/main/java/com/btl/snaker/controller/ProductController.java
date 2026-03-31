package com.btl.snaker.controller;

import com.btl.snaker.dto.ProductDTO;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.CreateProductRequest;
import com.btl.snaker.payload.request.UpdateProductRequest;
import com.btl.snaker.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllProducts() {
        ResponseData responseData = new ResponseData();
        responseData.setData(productServiceImp.getAllProducts());
        responseData.setSuccess(true);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        return new ResponseEntity<>(productServiceImp.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/admin/search")
    public ResponseEntity<?> getProductBySearch(@RequestParam String name) {
        return new ResponseEntity<>(productServiceImp.getProductByName(name), HttpStatus.OK);
    }

    @GetMapping("/user/search")
    public ResponseEntity<?> getProductsByName(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long priceFrom,
            @RequestParam(required = false) Long priceTo,
            @RequestParam(required = false) String name) {
        ResponseData responseData = new ResponseData();
        try {
            List<ProductDTO> productDTOS = productServiceImp.getProducts(categoryId, brandId, priceFrom, priceTo, name);
            responseData.setData(productDTOS);
            responseData.setSuccess(true);
            responseData.setDescription("Success");
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("Fail");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        ResponseData responseData = productServiceImp.createProduct(createProductRequest);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/admin/update")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRequest updateProductRequest) {
        ResponseData responseData = productServiceImp.updateProduct(updateProductRequest);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam long id) {
        ResponseData responseData = productServiceImp.deleteProduct(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
