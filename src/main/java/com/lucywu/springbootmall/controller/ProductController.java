package com.lucywu.springbootmall.controller;

import com.lucywu.springbootmall.constant.ProductCategory;
import com.lucywu.springbootmall.dto.ProductQueryParams;
import com.lucywu.springbootmall.dto.ProductRequest;
import com.lucywu.springbootmall.model.Product;
import com.lucywu.springbootmall.service.ProductService;
import com.lucywu.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
@Validated
@RestController

public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // Filter condition
            @RequestParam (required = false) ProductCategory category,
            @RequestParam (required = false) String search,
            // Sorting
            // orderBy param-- sort based on which column
            // sort param -- asc/desc
            @RequestParam (defaultValue = "created_date") String orderBy,
            @RequestParam (defaultValue = "desc") String sort,
            // Pagination
            // limit params -- limit the number of rows returned
            // offset param -- skips the number of before beginning to return the rows
            @RequestParam (defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam (defaultValue = "0") @Min(0) Integer offset
    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // get product list
        List<Product> productList = productService.getProducts(productQueryParams);

        // product total count will be different based on the query, for example only count CAR category product
        Integer total = productService.countProduct(productQueryParams);

        // pagination
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);

    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if (product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping("/products/{productId}")

    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){
        // check if product exists or not
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // update product
        productService.updateProduct(productId,productRequest);
        Product updateedProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateedProduct);

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
