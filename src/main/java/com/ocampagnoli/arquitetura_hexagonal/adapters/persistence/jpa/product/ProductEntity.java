package com.ocampagnoli.arquitetura_hexagonal.adapters.persistence.jpa.product;

import com.ocampagnoli.arquitetura_hexagonal.application.product.Product;
import com.ocampagnoli.arquitetura_hexagonal.application.product.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .status(product.getStatus())
                .build();
    }

    public static Product toDomain(ProductEntity product) {
        return Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .status(product.getStatus())
                .build();
    }
}
