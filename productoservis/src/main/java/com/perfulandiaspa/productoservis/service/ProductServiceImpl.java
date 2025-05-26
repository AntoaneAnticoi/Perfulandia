package com.perfulandiaspa.productoservis.service;


import com.perfulandiaspa.productoservis.model.Product;
import com.perfulandiaspa.productoservis.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
    
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(String id, Product productDetails) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
           
            if (productDetails.getName() != null) existingProduct.setName(productDetails.getName());
            if (productDetails.getPrice() > 0) existingProduct.setPrice(productDetails.getPrice());
            if (productDetails.getStock() >= 0) existingProduct.setStock(productDetails.getStock());
            if (productDetails.getDescription() != null) existingProduct.setDescription(productDetails.getDescription());

            return Optional.of(productRepository.save(existingProduct));
        }
        return Optional.empty(); 
    }

    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}