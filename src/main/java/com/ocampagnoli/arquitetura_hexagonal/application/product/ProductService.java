package com.ocampagnoli.arquitetura_hexagonal.application.product;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final IProductPersistence persistence;

    @Override
    public IProduct get(String id) {
        return persistence.get(id);
    }

    @Override
    public IProduct save(Product product) {
        if(Objects.isNull(product.getId())) {
            product.setId(UUID.randomUUID());
        }
        return persistence.save(product);
    }

    @Override
    public IProduct disable(Product product) {
        product.disable();
        return persistence.save(product);
    }

    @Override
    public IProduct enable(Product product) {
        product.enable();
        return persistence.save(product);
    }

    @Override
    public IProduct create(String name, BigDecimal price) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.isValid();
        return persistence.save(newProduct);
    }
}
