package com.ocampagnoli.arquitetura_hexagonal.adapters.persistence.jpa.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
