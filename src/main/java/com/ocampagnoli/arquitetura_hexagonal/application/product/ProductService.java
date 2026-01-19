package com.ocampagnoli.arquitetura_hexagonal.application.product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductService implements ProductServiceInterface {

    private final ProductPersistenceInterface persistence;

    public ProductService(ProductPersistenceInterface persistence) {
        this.persistence = persistence;
    }

    @Override
    public ProductInterface get(String id) {
        return persistence.get(id);
    }

    @Override
    public ProductInterface create(String name, BigDecimal price) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(name);
        product.setPrice(price);
        product.setStatus(ProductStatus.DISABLED);

        product.isValid(product);

        return persistence.save(product);
    }

    @Override
    public ProductInterface enable(ProductInterface product) {
        product.enable((Product) product);
        return persistence.save(product);
    }

    @Override
    public ProductInterface disable(ProductInterface product) {
        product.disable((Product) product);
        return persistence.save(product);
    }
}
