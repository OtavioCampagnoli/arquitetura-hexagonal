package com.ocampagnoli.arquitetura_hexagonal.adapters.persistence.jpa.product;

import com.ocampagnoli.arquitetura_hexagonal.application.product.IProduct;
import com.ocampagnoli.arquitetura_hexagonal.application.product.IProductPersistence;
import com.ocampagnoli.arquitetura_hexagonal.application.product.Product;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class ProductPersistenceAdapter implements IProductPersistence {

    private final IProductRepository repository;

    private ProductEntity findEntityOrThrow(UUID id) {
        log.info("Finding product entity with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    @Override
    public IProduct get(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            log.error("Invalid UUID format: {}", id, ex);
            throw new ProductNotFoundException(id);
        }
        return ProductEntity.toDomain(findEntityOrThrow(uuid));
    }

    @Override
    public IProduct save(Product product) {
        log.info("Saving product: {}", product);
        ProductEntity entity = ProductEntity.toEntity(product);
        log.info("Converted to entity: {}", entity);
        ProductEntity saved = repository.save(entity);
        log.info("Saved entity: {}", saved);
        return ProductEntity.toDomain(saved);
    }

    @Override
    public IProduct disable(Product product) {
        log.info("Disabling product: {}", product);
        ProductEntity entity = findEntityOrThrow(product.getId());
        ProductEntity saved = repository.save(entity);
        return ProductEntity.toDomain(saved);
    }

    @Override
    public IProduct enable(Product product) {
        log.info("Enabling product: {}", product);
        ProductEntity entity = findEntityOrThrow(product.getId());
        ProductEntity saved = repository.save(entity);
        return ProductEntity.toDomain(saved);
    }
}