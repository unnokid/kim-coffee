package com.example.kimcoffee.controller;

import com.example.kimcoffee.model.Email;

import java.util.UUID;

public class CreateOrderRequest {
    private final String email;
    private final String address;
    private final String postcode;
    private final UUID productId;
    private final long quantity;

    public CreateOrderRequest(String email, String address, String postcode, UUID productId, long quantity) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
