package com.btl.snaker.controller;


import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.CartRequest;
import com.btl.snaker.service.imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceImp cartServiceImp;

    @GetMapping("/get")
    public ResponseEntity<?> getCartByUser(@RequestParam long userId){
        ResponseData responseData = cartServiceImp.getCart(userId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertToCart(@RequestBody CartRequest cartRequest){
        ResponseData responseData = cartServiceImp.insertToCart(cartRequest);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/delete/item")
    public ResponseEntity<?> deleteFromCart(@RequestParam long cartItemId){
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(cartServiceImp.deleteCartItem(cartItemId));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllFromCart(@RequestParam long userId){
        ResponseData responseData = cartServiceImp.deleteAllCartItems(userId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
