package com.example.pagos.pagos.service;

import com.example.pagos.pagos.model.Pagos;
import com.example.pagos.pagos.repository.PagosRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagosService {

    private final PagosRepository pagosRepository;


    public PagosService(PagosRepository pagosRepository) {
        this.pagosRepository = pagosRepository;
    }


    public Pagos realizarPago(Pagos pago) {
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstado("Completado");
        return pagosRepository.save(pago);
    }


    public List<Pagos> obtenerPagosPorUsuario(Long usuarioId) {
        return pagosRepository.findByUsuarioId(usuarioId);
    }


    public List<Pagos> obtenerTodosLosPagos() {
        return pagosRepository.findAll();
    }


    public Optional<Pagos> obtenerPagoPorId(Long id) {
        return pagosRepository.findById(id);
    }
}
