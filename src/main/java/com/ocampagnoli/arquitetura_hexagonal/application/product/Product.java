package com.ocampagnoli.arquitetura_hexagonal.application.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements IProduct {
    private UUID id;
    private String name;
    private BigDecimal price;
    private ProductStatus status;

    @Override
    public void isValid() throws IllegalArgumentException {
        if (this.status == null) {
            this.status = ProductStatus.DISABLED;
        }

        if (this.status != ProductStatus.ENABLED && this.status != ProductStatus.DISABLED) {
            throw new IllegalArgumentException("The status must be enabled or disabled");
        }

        if (this.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The price must be greater or equal zero");
        }

        if (this.getName().isBlank()) {
            throw new IllegalArgumentException("The name must be defined");
        }
    }

    @Override
    public void disable() {
        if (Objects.nonNull(this.getPrice()) && this.getPrice().equals(BigDecimal.ZERO)) {
            this.status = ProductStatus.DISABLED;
            return;
        }
        throw new IllegalArgumentException("The price must be zero to disable the product");
    }

    @Override
    public void enable() {
        if (this.price.compareTo(BigDecimal.ZERO) > 0) {
            this.status = ProductStatus.ENABLED;
            return;
        }
        throw new IllegalArgumentException("The price must be greater than zero to enable the product");
    }

    @Override
    public void changePrice(BigDecimal price) throws IllegalArgumentException {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The price must be greater or equal zero");
        }
    }
}