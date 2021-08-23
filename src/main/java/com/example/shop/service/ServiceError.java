package com.example.shop.service;

public enum ServiceError {

    PRODUCT_NOT_FOUND (100, "Product not found."),
    ERROR_RETRIEVING_PRODUCT (101, "Error at retrieving the product."),
    ERROR_PARSING_PRODUCT (102, "Error at parse the product.");

    private final Integer code;
    private final String description;

    ServiceError(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
