package com.example.pagos.pagos.controller;

import com.example.pagos.pagos.model.Pagos;
import com.example.pagos.pagos.service.PagosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagosController {

    private final PagosService pagosService;

    public PagosController(PagosService pagosService) {
        this.pagosService = pagosService;
    }


    @PostMapping
    public ResponseEntity<Pagos> realizarPago(@RequestBody Pagos pago) {
        Pagos nuevoPago = pagosService.realizarPago(pago);
        return ResponseEntity.ok(nuevoPago);
    }


    @GetMapping
    public ResponseEntity<List<Pagos>> obtenerTodosLosPagos() {
        return ResponseEntity.ok(pagosService.obtenerTodosLosPagos());
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pagos>> obtenerPagosPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pagosService.obtenerPagosPorUsuario(usuarioId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pagos> obtenerPagoPorId(@PathVariable Long id) {
        return pagosService.obtenerPagoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
