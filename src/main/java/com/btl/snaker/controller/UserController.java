package com.btl.snaker.controller;

import com.btl.snaker.entity.User;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.repository.UserRepository;
import com.btl.snaker.service.MailService;
import com.btl.snaker.service.imp.MailServiceImp;
import com.btl.snaker.service.imp.UserServiceImp;
import com.btl.snaker.utils.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtilHelper jwtUtilHelper;
    @Autowired
    private MailServiceImp mailServiceImp;


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
    @PostMapping("/forgot/send")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        ResponseData responseData=new ResponseData();
        if(!userRepository.existsByEmail(email)){
            responseData.setSuccess(false);
            responseData.setDescription("User not found");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        User user = userRepository.findByEmail(email);
        Timestamp createCodeAt = user.getCreateCodeAt();

        if(createCodeAt != null) {
            long minutesSinceLastSent = ChronoUnit.SECONDS.between(createCodeAt.toLocalDateTime(), LocalDateTime.now());
            if (minutesSinceLastSent < 60) {
                responseData.setSuccess(false);
                responseData.setDescription("Wait 60s!");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
        }

        String resetCode = mailService.generateResetCode(email);
        String fullname = user.getFullname();
        responseData = mailServiceImp.sentForgotPasswordMail(fullname, email, resetCode);

        if (responseData.isSuccess()) {
            user.setCreateCodeAt(Timestamp.valueOf(LocalDateTime.now()));
            user.setVerifyCode(resetCode);
            userRepository.save(user);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/forgot/verify")
    public ResponseEntity<?> verifyForgotPassword(@RequestParam String email, @RequestParam String code){
        ResponseData responseData = mailServiceImp.validateCode(email, code);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("forgot/reset/password")
    public ResponseEntity<?> resetForgotPassword(@RequestParam String email,
                                                 @RequestParam String code,
                                                 @RequestParam String newPassword){
        ResponseData responseData = userServiceImp.resetPassword(email, code, newPassword);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
