package com.example.carrito1.carrito1.repository;

import com.example.carrito1.carrito1.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {

    // Buscar todos los items de un carrito por su ID
    List<ItemCarrito> findByCarrito_Id(Long carritoId);
}
