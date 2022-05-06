package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.Category;
import com.example.kimcoffee.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.kimcoffee.JdbcUtils.toLocalDateTime;
import static com.example.kimcoffee.JdbcUtils.toUUID;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product insert(Product product) {
        var update = jdbcTemplate.update("insert into products(product_id,product_name,category,price,description,create_at,update_at)" +
                "values(UNHEX(REPLACE(:productId, '-', '')), :productName, :category, :price, :description, :createAt,:updateAt)", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Failed insert");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper);
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return Optional.empty();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    private RowMapper<Product> productRowMapper = (resultSet, num) -> {
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        String productName = resultSet.getString("product_name");
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        String description = resultSet.getString("description");
        LocalDateTime createAt = toLocalDateTime(resultSet.getTimestamp("create_at"));
        LocalDateTime updateAt = toLocalDateTime(resultSet.getTimestamp("update_at"));

        return new Product(productId, productName, category, price, description, createAt, updateAt);
    };

    private Map<String, Object> toParamMap(Product product){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("productId", product.getProductId().toString().getBytes());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("createAt", product.getCreateAt());
        paramMap.put("updateAt", product.getUpdateAt());
        return paramMap;
    }

}
