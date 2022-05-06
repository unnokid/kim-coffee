package com.example.kimcoffee.controller;

import java.util.UUID;

public class DeleteProductRequest {
    private final UUID productId;

    public DeleteProductRequest(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
