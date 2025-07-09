package com.perfulandiaspa.productoservis.service;

import com.perfulandiaspa.productoservis.model.Product;
import com.perfulandiaspa.productoservis.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void testGetProductById_ReturnsProduct() {
        String id = UUID.randomUUID().toString();
        Product product = new Product(id, "Perfume X", 12000, 5, "Fresco");
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(id);
        assertTrue(result.isPresent());
        assertEquals("Perfume X", result.get().getName());
    }

    @Test
    void testAddProduct_SavesAndReturnsProduct() {
        Product product = new Product(null, "Nuevo Perfume", 15000, 2, "Dulce");
        Product savedProduct = new Product("1234", "Nuevo Perfume", 15000, 2, "Dulce");

        when(productRepository.save(product)).thenReturn(savedProduct);

        Product result = productService.addProduct(product);

        assertEquals("Nuevo Perfume", result.getName());
        verify(productRepository, times(1)).save(product);
    }
}
