package com.lucywu.springbootmall.service;

import com.lucywu.springbootmall.constant.ProductCategory;
import com.lucywu.springbootmall.dto.ProductRequest;
import com.lucywu.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductCategory category,String search);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);

}
