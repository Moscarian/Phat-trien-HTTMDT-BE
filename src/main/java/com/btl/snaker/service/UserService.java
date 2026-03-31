package com.btl.snaker.service;

import com.btl.snaker.dto.UserDTO;
import com.btl.snaker.entity.Role;
import com.btl.snaker.entity.User;
import com.btl.snaker.mapper.UserMapper;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.repository.UserRepository;
import com.btl.snaker.service.imp.MailServiceImp;
import com.btl.snaker.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailServiceImp mailServiceImp;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toDTOList(users);
    }

    @Override
    public ResponseData getUserById(long id) {
        ResponseData responseData = new ResponseData();
        User user = userRepository.findById(id);
        if(user == null) {
            responseData.setSuccess(false);
            responseData.setDescription("User not found");
            return responseData;
        }
        responseData.setSuccess(true);
        responseData.setData(UserMapper.toUserDTO(user));
        return responseData;
    }

    @Override
    public ResponseData getUserByEmail(String email) {
        ResponseData responseData = new ResponseData();
        User user = userRepository.findByEmail(email);
        if(user == null) {
            responseData.setSuccess(false);
            responseData.setDescription("User not found");
            return responseData;
        }
        responseData.setSuccess(true);
        responseData.setData(UserMapper.toUserDTO(user));
        return responseData;
    }

    @Override
    public Boolean handleUser(long id) {
        User user = userRepository.findById(id);
        if(user != null) {
            if(user.getActive() == 0){
                user.setActive(1);
                userRepository.save(user);
                return true;
            }
            else{
                user.setActive(0);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseData changePassword(long id, String oldPassword, String newPassword) {
        ResponseData responseData = new ResponseData();
        User user = userRepository.findById(id);
        if(user == null) {
            responseData.setSuccess(false);
            responseData.setDescription("User not found");
            return responseData;
        }
        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            responseData.setSuccess(false);
            responseData.setDescription("Wrong password");
            return responseData;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        responseData.setSuccess(true);
        responseData.setDescription("Successful");
        return responseData;
    }

    @Override
    public ResponseData resetPassword(String email, String code, String newPassword) {
        ResponseData responseDataRes = new ResponseData();
        ResponseData responseData = mailServiceImp.validateCode(email, code);
        if(responseData.isSuccess()) {
            User user = userRepository.findByEmail(email);
            if(passwordEncoder.matches(newPassword, user.getPassword())){
                responseDataRes.setSuccess(false);
                responseDataRes.setExist(true);
                return responseDataRes;
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            responseDataRes.setSuccess(true);
        }
        return responseDataRes;
    }

    @Override
    public ResponseData updateInformation(long id, String phone, String address) {
        ResponseData responseData = new ResponseData();
        User user = userRepository.findById(id);
        if(user == null) {
            responseData.setSuccess(false);
            responseData.setDescription("User not found");
            return responseData;
        }
        user.setPhone(phone);
        user.setAddress(address);
        userRepository.save(user);
        responseData.setSuccess(true);
        responseData.setDescription("Successful");
        return responseData;
    }
}
