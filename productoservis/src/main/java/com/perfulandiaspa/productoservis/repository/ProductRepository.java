package com.perfulandiaspa.productoservis.repository;


import com.perfulandiaspa.productoservis.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository 
public interface ProductRepository extends JpaRepository<Product, String> {
    
}