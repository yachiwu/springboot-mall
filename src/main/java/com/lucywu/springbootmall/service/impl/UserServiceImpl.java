package com.lucywu.springbootmall.service.impl;

import com.lucywu.springbootmall.dao.UserDao;
import com.lucywu.springbootmall.dto.UserRegisterRequest;
import com.lucywu.springbootmall.model.User;
import com.lucywu.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

}
