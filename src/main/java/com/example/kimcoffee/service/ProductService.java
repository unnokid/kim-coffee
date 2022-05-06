package com.example.kimcoffee.service;

import com.example.kimcoffee.model.Category;
import com.example.kimcoffee.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(String productName, Category category, long price);
    Product createProduct(String productName, Category category, long price, String description);
    List<Product> getAllProducts();
}
