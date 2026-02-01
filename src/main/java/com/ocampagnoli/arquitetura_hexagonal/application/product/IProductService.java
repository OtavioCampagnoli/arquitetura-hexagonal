package com.ocampagnoli.arquitetura_hexagonal.application.product;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface IProductService extends IProductPersistence {
    IProduct create(String name, BigDecimal price);
}
