package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.Category;
import com.example.kimcoffee.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product insert(Product product);
    Product update(Product product);
    List<Product> findAll();
    Optional<Product> findById(UUID productId);
    Optional<Product> findByName(String productName);
    List<Product> findByCategory(Category category);
    void deleteAll();
}
