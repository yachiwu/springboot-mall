package com.lucywu.springbootmall.rowmapper;

import com.lucywu.springbootmall.constant.ProductCategory;
import com.lucywu.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
// RowMapper 用途: 將資料庫查詢出來的數據轉換成java object
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        // 從資料庫中取得數據，將數據轉成Java object
        Product product = new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));

        // 根據資料庫中category 的string 找尋ProductCategory中的對應值
        String categoryStr = resultSet.getString("category");
        ProductCategory category = ProductCategory.valueOf(categoryStr);
        product.setCategory(category);
        //product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));

        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreatedDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
        return product;

    }
}
