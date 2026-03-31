package com.btl.snaker.controller;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(userServiceImp.getAllUsers());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser(@RequestParam long id) {
        ResponseData responseData = userServiceImp.getUserById(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/user/change/password")
    public ResponseEntity<?> changePassword(@RequestParam long id,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        return new ResponseEntity<>(userServiceImp.changePassword(id, oldPassword, newPassword), HttpStatus.OK);
    }

    @PostMapping("/user/change/information")
    public ResponseEntity<?> changeInformation(@RequestParam long id,
                                               @RequestParam String phone,
                                               @RequestParam String address){
        return new ResponseEntity<>(userServiceImp.updateInformation(id, phone, address), HttpStatus.OK);
    }

    @PostMapping("/admin/handle/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        ResponseData responseData=new ResponseData();
        boolean isSuccess = userServiceImp.handleUser(id);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
