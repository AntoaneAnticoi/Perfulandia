package com.example.pagos.pagos.repository;

import com.example.pagos.pagos.model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagosRepository extends JpaRepository<Pagos, Long> {
    List<Pagos> findByUsuarioId(Long usuarioId);
}
