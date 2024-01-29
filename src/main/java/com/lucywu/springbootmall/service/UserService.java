package com.lucywu.springbootmall.service;

import com.lucywu.springbootmall.dto.UserRegisterRequest;
import com.lucywu.springbootmall.model.User;

public interface UserService {
    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);

}
