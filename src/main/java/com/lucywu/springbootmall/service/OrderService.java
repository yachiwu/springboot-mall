package com.lucywu.springbootmall.service;

import com.lucywu.springbootmall.dto.CreateOrderRequest;
import com.lucywu.springbootmall.dto.OrderQueryParams;
import com.lucywu.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
