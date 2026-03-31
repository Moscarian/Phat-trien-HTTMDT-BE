package com.btl.snaker.controller;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.SignupRequest;
import com.btl.snaker.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginServiceImp loginServiceImp;

    @PostMapping("/signin")
   public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password) {
        ResponseData responseData = new ResponseData();
        responseData = loginServiceImp.checkLogin(email, password);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        ResponseData responseData = loginServiceImp.signup(signupRequest);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
