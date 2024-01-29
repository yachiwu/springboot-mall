package com.lucywu.springbootmall.dao;

import com.lucywu.springbootmall.dto.UserRegisterRequest;
import com.lucywu.springbootmall.model.User;

public interface UserDao {
    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);

}
