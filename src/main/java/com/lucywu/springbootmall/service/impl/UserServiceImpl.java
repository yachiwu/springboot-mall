package com.lucywu.springbootmall.service.impl;

import com.lucywu.springbootmall.dao.UserDao;
import com.lucywu.springbootmall.dto.UserLoginRequest;
import com.lucywu.springbootmall.dto.UserRegisterRequest;
import com.lucywu.springbootmall.model.User;
import com.lucywu.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {
    private  final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // check if user email already exist or not
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user!= null){
            log.warn("Email {} has been registered",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // use MD5 to generate password hash
        String hashedPassword  = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        // check if user exist
        if (user == null){
            log.warn("Email hasn't yet been registered",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // use MD5 to generate password hash
        String hashedPassword  = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        // compare password
        if (user.getPassword().equals(hashedPassword)){
            return user;
        }else{
            log.warn("Email {} 's password is incorrect",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
