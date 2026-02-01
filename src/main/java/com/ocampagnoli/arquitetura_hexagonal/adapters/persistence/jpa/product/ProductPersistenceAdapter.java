package com.ocampagnoli.arquitetura_hexagonal.adapters.persistence.jpa.product;

import com.ocampagnoli.arquitetura_hexagonal.application.product.IProduct;
import com.ocampagnoli.arquitetura_hexagonal.application.product.IProductPersistence;
import com.ocampagnoli.arquitetura_hexagonal.application.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductPersistenceAdapter implements IProductPersistence {

    private final IProductRepository repository;

    private ProductEntity findEntityOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    @Override
    public IProduct get(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new ProductNotFoundException(id);
        }

        return ProductEntity.toDomain(findEntityOrThrow(uuid));
    }

    @Override
    public IProduct save(Product product) {
        ProductEntity entity = ProductEntity.toEntity(product);
        ProductEntity saved = repository.save(entity);
        return ProductEntity.toDomain(saved);
    }

    @Override
    public IProduct disable(Product product) {
        ProductEntity entity = findEntityOrThrow(product.getId());
        ProductEntity saved = repository.save(entity);
        return ProductEntity.toDomain(saved);
    }

    @Override
    public IProduct enable(Product product) {
        ProductEntity entity = findEntityOrThrow(product.getId());
        ProductEntity saved = repository.save(entity);
        return ProductEntity.toDomain(saved);
    }
}