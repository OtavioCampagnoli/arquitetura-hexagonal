package com.ocampagnoli.arquitetura_hexagonal.adapters.persistence.jpa.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<ProductEntity, UUID> {
}
