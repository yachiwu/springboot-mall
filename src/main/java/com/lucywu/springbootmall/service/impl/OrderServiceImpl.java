package com.lucywu.springbootmall.service.impl;

import com.lucywu.springbootmall.dao.OrderDao;
import com.lucywu.springbootmall.dao.ProductDao;
import com.lucywu.springbootmall.dto.BuyItem;
import com.lucywu.springbootmall.dto.CreateOrderRequest;
import com.lucywu.springbootmall.model.Order;
import com.lucywu.springbootmall.model.OrderItem;
import com.lucywu.springbootmall.model.Product;
import com.lucywu.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //  calculate total amount
        Integer totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            Integer amount = product.getPrice() * buyItem.getQuantity();
            totalAmount = totalAmount + amount;

            // convert buyItem to orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }
        // create order
        Integer orderId = orderDao.createOrder(userId,totalAmount);
        // create order items
        orderDao.createOrderItems(orderId, orderItemList);
        return orderId;
    }
}
