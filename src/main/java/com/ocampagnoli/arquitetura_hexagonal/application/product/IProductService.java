package com.ocampagnoli.arquitetura_hexagonal.application.product;

import java.math.BigDecimal;

public interface IProductService extends IProductPersistence {
    IProduct create(String name, BigDecimal price);
}
