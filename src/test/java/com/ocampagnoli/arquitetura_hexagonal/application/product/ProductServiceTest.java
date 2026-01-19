package com.ocampagnoli.arquitetura_hexagonal.application.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductPersistenceInterface persistence;

    @InjectMocks
    private ProductService service;

    @Test
    void testProductService_create() {
        when(persistence.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.create("Product Test", new BigDecimal("20.00"));

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(persistence).save(captor.capture());

        Product savedProduct = captor.getValue();
        Assertions.assertNotNull(savedProduct);
        Assertions.assertNotNull(savedProduct.getId());
        Assertions.assertEquals("Product Test", savedProduct.getName());
        Assertions.assertEquals(new BigDecimal("20.00"), savedProduct.getPrice());
        Assertions.assertEquals(ProductStatus.DISABLED, savedProduct.getStatus());
    }

    @Test
    void testProductService_enable() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Product Test");
        product.setPrice(new BigDecimal("10.00"));
        product.setStatus(ProductStatus.DISABLED);

        when(persistence.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.enable(product);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(persistence).save(captor.capture());

        Product savedProduct = captor.getValue();
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(ProductStatus.ENABLED, savedProduct.getStatus());
    }

    @Test
    void testProductService_disable() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Product Test");
        product.setPrice(new BigDecimal("0"));
        product.setStatus(ProductStatus.ENABLED);

        when(persistence.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.disable(product);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(persistence).save(captor.capture());

        Product savedProduct = captor.getValue();
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(ProductStatus.DISABLED, savedProduct.getStatus());
    }
}
