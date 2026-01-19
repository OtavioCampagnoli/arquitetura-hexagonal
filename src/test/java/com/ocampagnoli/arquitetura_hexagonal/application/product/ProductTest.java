package com.ocampagnoli.arquitetura_hexagonal.application.product;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductTest {

    @Test
    void testProduct_enable_validPrice() {
        Product product = new Product();
        product.setName("Product Test");
        product.setPrice(new BigDecimal("10.00"));
        product.enable(product);
        Assertions.assertEquals(ProductStatus.ENABLED, product.getStatus());
    }

    @Test
    void testProduct_enable_invalidPrice() {
        Product product = new Product();
        product.setName("Product Test");
        product.setPrice(new BigDecimal("0"));
        assertThrows(IllegalArgumentException.class, () -> product.enable(product), "The price must be greater than zero to enable the product");
    }

    @Test
    void testProduct_disable_validPrice() {
        Product product = new Product();
        product.setName("Product Test");
        product.setPrice(BigDecimal.ZERO);
        product.disable(product);
        Assertions.assertEquals(ProductStatus.DISABLED, product.getStatus());
    }

    @Test
    void testProduct_disable_invalidPrice() {
        Product product = new Product();
        product.setName("Product Test");
        assertThrows(IllegalArgumentException.class, () -> product.disable(product), "The price must be zero to disable the product");
    }

    @Test
    void testProduct_isValid_invalidPrice() {
        Product product = new Product();
        product.setPrice(new BigDecimal("-10.00"));
        assertThrows(IllegalArgumentException.class, () -> product.isValid(product), "The price must be greater or equal zero");
    }

    @Test
    void testProduct_isValid_invalidName() {
        Product product = new Product();
        product.setPrice(new BigDecimal("20.00"));
        product.setName("");
        assertThrows(IllegalArgumentException.class, () -> product.isValid(product), "The name must be defined");
    }

    @Test
    void testProduct_isValid_invalidStatus() {
        Product product = new Product();
        product.setPrice(new BigDecimal("20.00"));
        product.setName("Product Test");
        assertThrows(IllegalArgumentException.class, () -> product.isValid(product), "The status must be enabled or disabled");
    }
}