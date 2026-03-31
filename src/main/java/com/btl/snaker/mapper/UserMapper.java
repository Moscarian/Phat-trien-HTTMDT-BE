package com.btl.snaker.mapper;

import com.btl.snaker.dto.UserDTO;
import com.btl.snaker.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullname());
        userDTO.setRole(user.getRole().getId() == 1 ? "Admin" : "User");
        userDTO.setToken(user.getToken());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        userDTO.setIsActive(user.getActive());
        return userDTO;
    }
    public static List<UserDTO> toDTOList(List<User> usersList) {
        List<UserDTO> usersDTOList = new ArrayList<>();
        for (User user : usersList) {
            usersDTOList.add(toUserDTO(user));
        }
        return usersDTOList;
    }
}
