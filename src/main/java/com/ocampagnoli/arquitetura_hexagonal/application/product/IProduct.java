package com.ocampagnoli.arquitetura_hexagonal.application.product;

import java.math.BigDecimal;

public interface IProduct {
    void isValid() throws IllegalArgumentException;
    void disable() throws IllegalArgumentException;
    void enable() throws IllegalArgumentException;
    void changePrice(BigDecimal price) throws IllegalArgumentException;
}
