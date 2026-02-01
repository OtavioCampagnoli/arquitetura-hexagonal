package com.ocampagnoli.arquitetura_hexagonal.adapters.persistence.jpa.product;

import com.ocampagnoli.arquitetura_hexagonal.application.product.IProduct;
import com.ocampagnoli.arquitetura_hexagonal.application.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductPersistenceAdapterTest {

    @Mock
    private IProductRepository repository;

    @InjectMocks
    private ProductPersistenceAdapter adapter;

    @Test
    void get_withInvalidUuid_shouldThrowProductNotFoundException() {
        String invalidId = "test-invalid-uuid";
        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            adapter.get(invalidId);
        });
    }

    @Test
    void get_withUnknownId_throwsProductNotFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> adapter.get(id.toString()));
        verify(repository).findById(id);
    }

    @Test
    void get_withExistingEntity_returnsDomain() {
        UUID id = UUID.randomUUID();
        ProductEntity entity = mock(ProductEntity.class);
        IProduct domain = mock(Product.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        try (MockedStatic<ProductEntity> mocked = Mockito.mockStatic(ProductEntity.class)) {
            mocked.when(() -> ProductEntity.toDomain(entity)).thenReturn(domain);

            IProduct result = adapter.get(id.toString());
            assertSame(domain, result);
        }

        verify(repository).findById(id);
    }

    @Test
    void save_convertsCallsRepositoryAndReturnsDomain() {
        Product product = mock(Product.class);
        ProductEntity entity = mock(ProductEntity.class);
        ProductEntity savedEntity = mock(ProductEntity.class);
        IProduct domain = mock(Product.class);

        try (MockedStatic<ProductEntity> mocked = Mockito.mockStatic(ProductEntity.class)) {
            mocked.when(() -> ProductEntity.toEntity(product)).thenReturn(entity);
            when(repository.save(entity)).thenReturn(savedEntity);
            mocked.when(() -> ProductEntity.toDomain(savedEntity)).thenReturn(domain);

            IProduct result = adapter.save(product);

            assertSame(domain, result);
            verify(repository).save(entity);
        }
    }

    @Test
    void disable_findsSavesAndReturnsDomain() {
        Product product = mock(Product.class);
        UUID id = UUID.randomUUID();
        when(product.getId()).thenReturn(id);

        ProductEntity entity = mock(ProductEntity.class);
        ProductEntity savedEntity = mock(ProductEntity.class);
        IProduct domain = mock(Product.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(savedEntity);

        try (MockedStatic<ProductEntity> mocked = Mockito.mockStatic(ProductEntity.class)) {
            mocked.when(() -> ProductEntity.toDomain(savedEntity)).thenReturn(domain);

            IProduct result = adapter.disable(product);

            assertSame(domain, result);
            verify(repository).findById(id);
            verify(repository).save(entity);
        }
    }

    @Test
    void enable_findsSavesAndReturnsDomain() {
        Product product = mock(Product.class);
        UUID id = UUID.randomUUID();
        when(product.getId()).thenReturn(id);

        ProductEntity entity = mock(ProductEntity.class);
        ProductEntity savedEntity = mock(ProductEntity.class);
        IProduct domain = mock(Product.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(savedEntity);

        try (MockedStatic<ProductEntity> mocked = Mockito.mockStatic(ProductEntity.class)) {
            mocked.when(() -> ProductEntity.toDomain(savedEntity)).thenReturn(domain);

            IProduct result = adapter.enable(product);

            assertSame(domain, result);
            verify(repository).findById(id);
            verify(repository).save(entity);
        }
    }


}
