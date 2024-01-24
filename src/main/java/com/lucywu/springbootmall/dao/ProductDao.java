package com.lucywu.springbootmall.dao;

import com.lucywu.springbootmall.dto.ProductRequest;
import com.lucywu.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
