package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.OrderItem;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.kimcoffee.JdbcUtils.toUUID;

@Repository
public class OrderItemJdbcRepository implements OrderItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderItemJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<OrderItem> findById(UUID orderId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("select * from order_items where order_id = UNHEX(REPLACE(:orderId, '-', ''))",
                            Collections.singletonMap("orderId", orderId.toString().getBytes()),
                            orderItemRowMapper
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public OrderItem insert(OrderItem orderItem) {
        jdbcTemplate.update("insert into order_items(order_id, product_id, price, quantity)" +
                        "values(UNHEX(REPLACE(:orderId, '-', '')), UNHEX(REPLACE(:productId, '-', '')),:price,:quantity)",
                orderItemParamMap(orderItem)
        );
        return orderItem;
    }

    private RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
        var orderId = toUUID(resultSet.getBytes("order_id"));
        var productId = toUUID(resultSet.getBytes("product_id"));
        var price = resultSet.getLong("price");
        var quantity = resultSet.getLong("quantity");
        return new OrderItem(orderId, productId, price, quantity);
    };

    private Map<String, Object> orderItemParamMap(OrderItem orderItem) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderItem.getOrderId().toString().getBytes());
        paramMap.put("productId", orderItem.getProductId().toString().getBytes());
        paramMap.put("price", orderItem.getPrice());
        paramMap.put("quantity", orderItem.getQuantity());
        return paramMap;
    }
}
