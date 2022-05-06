package com.example.kimcoffee.controller;

public class CategoryRequest {
    private final String category;

    public CategoryRequest(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
