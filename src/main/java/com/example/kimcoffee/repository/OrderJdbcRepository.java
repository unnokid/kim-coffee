package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.Email;
import com.example.kimcoffee.model.Order;
import com.example.kimcoffee.model.OrderStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.kimcoffee.JdbcUtils.toLocalDateTime;
import static com.example.kimcoffee.JdbcUtils.toUUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order insert(Order order) {
        jdbcTemplate.update("insert into orders(order_id, email, address, postcode, order_status, create_at, update_at)" +
                        "values(UNHEX(REPLACE(:orderId, '-', '')), :email,:address, :postcode, :orderStatus, :createAt, :updateAt)",
                orderParamMap(order)
        );
        return order;
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("select * from orders",
                orderRowMapper);
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("select * from orders where order_id = UNHEX(REPLACE(:orderId, '-', ''))",
                            Collections.singletonMap("orderId", orderId.toString().getBytes()),
                            orderRowMapper
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Order> orderRowMapper = (resultSet, i) -> {
        var orderId = toUUID(resultSet.getBytes("order_id"));
        var email = resultSet.getString("email");
        var address = resultSet.getString("address");
        var postcode = resultSet.getString("postcode");
        var orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        var createAt = toLocalDateTime(resultSet.getTimestamp("create_at"));
        var updateAt = toLocalDateTime(resultSet.getTimestamp("update_at"));
        return new Order(orderId, new Email(email), address, postcode, orderStatus, createAt, updateAt);
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

}
