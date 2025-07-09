package com.example.pagos.pagos.controller;

import com.example.pagos.pagos.model.Pagos;
import com.example.pagos.pagos.service.PagosService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
    public ResponseEntity<CollectionModel<EntityModel<Pagos>>> obtenerTodosLosPagos() {
        List<Pagos> pagos = pagosService.obtenerTodosLosPagos();

        List<EntityModel<Pagos>> pagosModel = pagos.stream()
                .map(pago -> EntityModel.of(pago,
                        linkTo(methodOn(PagosController.class).obtenerPagoPorId(pago.getId())).withSelfRel(),
                        linkTo(methodOn(PagosController.class).obtenerTodosLosPagos()).withRel("pagos")))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pagos>> collectionModel = CollectionModel.of(pagosModel,
                linkTo(methodOn(PagosController.class).obtenerTodosLosPagos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
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

