package com.lucywu.springbootmall.service;

import com.lucywu.springbootmall.dto.ProductRequest;
import com.lucywu.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
}
