package com.lucywu.springbootmall.service;

import com.lucywu.springbootmall.dto.CreateOrderRequest;
import com.lucywu.springbootmall.model.Order;

public interface OrderService {
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
