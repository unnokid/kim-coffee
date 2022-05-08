package com.example.kimcoffee.service;

import com.example.kimcoffee.model.Category;
import com.example.kimcoffee.model.Product;
import com.example.kimcoffee.repository.ProductJdbcRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

    private final ProductJdbcRepository productJdbcRepository;

    public DefaultProductService(ProductJdbcRepository productJdbcRepository) {
        this.productJdbcRepository = productJdbcRepository;
    }

    @Override
    public Product createProduct(String productName, Category category, long price, String description) {
        Product product = new Product(UUID.randomUUID(), productName, category, price, description, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return productJdbcRepository.insert(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productJdbcRepository.findAll();
    }

    @Override
    public void deleteProduct(UUID productId) {
        productJdbcRepository.deleteById(productId);
    }

    @Override
    public List<Product> getFindByCategory(Category category) {
        return productJdbcRepository.findByCategory(category);
    }

    @Override
    public Optional<Product> getFindById(UUID productId) {
        return productJdbcRepository.findById(productId);
    }
}
