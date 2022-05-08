package com.example.kimcoffee.controller;

import java.util.UUID;

public class UUIDRequest {
    private final UUID id;

    public UUIDRequest(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
