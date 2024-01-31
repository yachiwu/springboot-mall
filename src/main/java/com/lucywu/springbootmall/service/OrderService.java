package com.lucywu.springbootmall.service;

import com.lucywu.springbootmall.dto.CreateOrderRequest;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
