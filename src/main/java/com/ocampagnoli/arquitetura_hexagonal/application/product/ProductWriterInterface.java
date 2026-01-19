package com.ocampagnoli.arquitetura_hexagonal.application.product;

public interface ProductWriterInterface {
    public ProductInterface save(ProductInterface product);
    public ProductInterface disable(ProductInterface product);
    public ProductInterface enable(ProductInterface product);
}
