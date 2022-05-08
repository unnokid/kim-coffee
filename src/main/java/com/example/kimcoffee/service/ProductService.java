package com.example.kimcoffee.service;

import com.example.kimcoffee.model.Category;
import com.example.kimcoffee.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Product createProduct(String productName, Category category, long price, String description);
    List<Product> getAllProducts();
    void deleteProduct(UUID productId);
    List<Product> getFindByCategory(Category category);
    Optional<Product> getFindById(UUID productId);
}
