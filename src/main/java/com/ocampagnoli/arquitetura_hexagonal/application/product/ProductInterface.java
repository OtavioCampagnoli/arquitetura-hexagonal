package com.ocampagnoli.arquitetura_hexagonal.application.product;

public interface ProductInterface {
    public boolean isValid(Product p);
    public void disable(Product p);
    public void enable(Product p);
}
