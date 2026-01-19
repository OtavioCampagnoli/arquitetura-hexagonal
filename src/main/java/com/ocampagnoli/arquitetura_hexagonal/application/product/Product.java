package com.ocampagnoli.arquitetura_hexagonal.application.product;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import lombok.Data;

@Data
public class Product implements ProductInterface {
    private UUID id;
    private String name;
    private BigDecimal price;
    private ProductStatus status;

    @Override
    public boolean isValid(Product p) {
        if(p.status != ProductStatus.ENABLED && p.getStatus() != ProductStatus.DISABLED) {
            throw new IllegalArgumentException("The status must be enabled or disabled");
        }

        if (p.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The price must be greater or equal zero");
        }

        if(p.getName().isBlank()) {
            throw new IllegalArgumentException("The name must be defined");
        }
        return true;
    }

    @Override
    public void disable(Product p) {
        if (Objects.nonNull(p.getPrice()) && p.getPrice().equals(BigDecimal.ZERO)) {
            p.status = ProductStatus.DISABLED;
            return;
        }
        throw new IllegalArgumentException("The price must be zero to disable the product");
    }

    @Override
    public void enable(Product p) {
        if (p.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            p.status = ProductStatus.ENABLED;
            return;
        }
        throw new IllegalArgumentException("The price must be greater than zero to enable the product");
    }

}