package com.btl.snaker.service.imp;


import com.btl.snaker.payload.ResponseData;

public interface MailServiceImp {
    ResponseData sentForgotPasswordMail(String fullname, String toAddress, String verifyCode);
    ResponseData validateCode(String email, String resetCode);
}
