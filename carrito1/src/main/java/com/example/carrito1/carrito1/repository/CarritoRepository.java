package com.example.carrito1.carrito1.repository;

import com.example.carrito1.carrito1.model.Carrito;
import org.springframework.data.repository.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioId(Long usuarioId);
}
