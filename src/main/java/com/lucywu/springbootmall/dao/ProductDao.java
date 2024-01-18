package com.lucywu.springbootmall.dao;

import com.lucywu.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
