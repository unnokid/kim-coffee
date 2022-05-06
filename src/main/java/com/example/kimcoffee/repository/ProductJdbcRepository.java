package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.Category;
import com.example.kimcoffee.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
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
        int update = jdbcTemplate.update("insert into products(product_id,product_name,category,price,description,create_at,update_at)" +
                "values(UNHEX(REPLACE(:productId, '-', '')), :productName, :category, :price, :description, :createAt,:updateAt)", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Failed insert");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        int update = jdbcTemplate.update(
                "update products set product_name=:productName, category=:category, price=:price," +
                        "description=:description, create_at=:createAt, update_at=:updateAt " +
                        "where product_id = UNHEX(REPLACE(:productId, '-', ''))",
                toParamMap(product)
        );
        if (update != 1) {
            throw new RuntimeException("Failed update");
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper);
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from products where product_id = UNHEX(REPLACE(:productId, '-', ''))",
                            Collections.singletonMap("productId", productId.toString().getBytes()),
                            productRowMapper
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from products where product_name = :productName",
                            Collections.singletonMap("productName", productName),
                            productRowMapper
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query(
                "select * from products where category = :category",
                Collections.singletonMap("category", category.toString()),
                productRowMapper
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from products", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID productId) {
        jdbcTemplate.update(
                "delete from products where product_id = UNHEX(REPLACE(:productId, '-', ''))",
                Collections.singletonMap("productId", productId.toString().getBytes())
        );
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

    private Map<String, Object> toParamMap(Product product) {
        Map<String, Object> paramMap = new HashMap<>();
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
