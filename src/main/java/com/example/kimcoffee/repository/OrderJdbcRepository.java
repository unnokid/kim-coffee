package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.kimcoffee.JdbcUtils.toLocalDateTime;
import static com.example.kimcoffee.JdbcUtils.toUUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order insert(Order order) {
        jdbcTemplate.update("insert into orders(order_id, email, address, postcode, order_status, create_at, update_at)" +
                        "values(UNHEX(REPLACE(:orderId, '-', '')), :email,:address, :postcode, :orderStatus, :createAt, :updateAt)",
                orderParamMap(order)
        );
        order.getOrderItems()
                .forEach(item ->
                        jdbcTemplate.update("insert into order_items(order_id, product_id, category, price, quantity, create_at, update_at)" +
                                        "values (UNHEX(REPLACE(:orderId, '-', '')), UNHEX(REPLACE(:productId, '-', '')), :category,:price, :quantity, :createAt, :updateAt)",
                                orderItemParamMap(order.getOrderId(), order.getCreateAt(), order.getUpdateAt(), item)));
        return order;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("select * from orders",
                orderRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from orders",
                Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID orderId) {

    }

    private RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
        var productId = toUUID(resultSet.getBytes("product_id"));
        var category   = Category.valueOf(resultSet.getString("category"));
        var price      = resultSet.getLong("price");
        var quantity   = resultSet.getLong("quantity");
        return new OrderItem(productId,category,price,quantity);
    };

    private RowMapper<Order> orderRowMapper = (resultSet, i) -> {
        var orderId = toUUID(resultSet.getBytes("order_id"));
        var email = resultSet.getString("email");
        var address = resultSet.getString("address");
        var postcode = resultSet.getString("postcode");
        var orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        var createAt = toLocalDateTime(resultSet.getTimestamp("create_at"));
        var updateAt = toLocalDateTime(resultSet.getTimestamp("update_at"));
//        List<OrderItem> orderItems = new ArrayList<>();
//        orderItems.add(jdbcTemplate.queryForObject(
//                "select * from order_items where order_id = UNHEX(REPLACE(:orderId, '-', ''))",
//                Collections.singletonMap("orderId",orderId),
//                orderItemRowMapper
//        ));
//        Order order = new Order(orderId, new Email(email),address,postcode,orderStatus,createAt,updateAt);
//        return order;
        return null;
    };

    private Map<String, Object> orderParamMap(Order order) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createAt", order.getCreateAt());
        paramMap.put("updateAt", order.getUpdateAt());
        return paramMap;
    }

    private Map<String, Object> orderItemParamMap(UUID orderId, LocalDateTime createAt, LocalDateTime updateAt, OrderItem item) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId.toString().getBytes());
        paramMap.put("productId", item.getProductId().toString().getBytes());
        paramMap.put("category", item.getCategory().toString());
        paramMap.put("price", item.getPrice());
        paramMap.put("quantity", item.getQuantity());
        paramMap.put("createAt", createAt);
        paramMap.put("updateAt", updateAt);

        return paramMap;
    }
}
