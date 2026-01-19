package com.ocampagnoli.arquitetura_hexagonal.application.product;

public enum ProductStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    private final String status;

    ProductStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}