package com.ocampagnoli.arquitetura_hexagonal.application.product;

public interface IProductWriter {
    public IProduct save(Product product);
    public IProduct disable(Product product);
    public IProduct enable(Product product);
}
