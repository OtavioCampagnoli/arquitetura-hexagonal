package com.ocampagnoli.arquitetura_hexagonal.application.product;

import java.math.BigDecimal;

public interface ProductServiceInterface {
    public ProductInterface get(String id);
    public ProductInterface create(String name, BigDecimal price);
    public ProductInterface enable(ProductInterface product);
    public ProductInterface disable(ProductInterface product);
}
