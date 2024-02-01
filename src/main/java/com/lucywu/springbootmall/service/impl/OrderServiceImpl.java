package com.lucywu.springbootmall.service.impl;

import com.lucywu.springbootmall.dao.OrderDao;
import com.lucywu.springbootmall.dao.ProductDao;
import com.lucywu.springbootmall.dao.UserDao;
import com.lucywu.springbootmall.dto.BuyItem;
import com.lucywu.springbootmall.dto.CreateOrderRequest;
import com.lucywu.springbootmall.model.Order;
import com.lucywu.springbootmall.model.OrderItem;
import com.lucywu.springbootmall.model.Product;
import com.lucywu.springbootmall.model.User;
import com.lucywu.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

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
        // check user exist
        User user = userDao.getUserById(userId);
        if (user == null) {
            log.warn("user {} not exist", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //  calculate total amount
        Integer totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            // check product is exist and stock is sufficient
            if (product == null) {
                log.warn("product {} not exist", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if (product.getStock() < buyItem.getQuantity()){
                log.warn("Product {} is insufficient. Product stock quantity {}. The quantity you want to buy {}",
                        buyItem.getProductId(), product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            // update the product stock
            productDao.updateStock(product.getProductId(), product.getStock()- buyItem.getQuantity());
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
