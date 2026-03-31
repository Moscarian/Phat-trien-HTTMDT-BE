package com.btl.snaker.service.imp;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.SignupRequest;

public interface LoginServiceImp {
    ResponseData checkLogin(String email, String password);
    Boolean isEmailExist(String email);
    ResponseData signup(SignupRequest signupRequest);
}
