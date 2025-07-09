package com.example.carrito1.carrito1.controller;

import com.example.carrito1.carrito1.model.Carrito;
import com.example.carrito1.carrito1.model.ItemCarrito;
import com.example.carrito1.carrito1.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<EntityModel<Carrito>> obtenerCarrito(@PathVariable Long usuarioId) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        EntityModel<Carrito> carritoModel = EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).obtenerCarrito(usuarioId)).withSelfRel(),
                linkTo(methodOn(CarritoController.class).listarItems(usuarioId)).withRel("items")
        );
        return ResponseEntity.ok(carritoModel);
    }

    @PostMapping("/{usuarioId}/agregar")
    public ResponseEntity<EntityModel<ItemCarrito>> agregarProducto(
            @PathVariable Long usuarioId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {

        ItemCarrito item = carritoService.agregarProducto(usuarioId, productoId, cantidad);

        EntityModel<ItemCarrito> itemModel = EntityModel.of(item,
                linkTo(methodOn(CarritoController.class).agregarProducto(usuarioId, productoId, cantidad)).withSelfRel(),
                linkTo(methodOn(CarritoController.class).obtenerCarrito(usuarioId)).withRel("carrito")
        );

        return ResponseEntity.ok(itemModel);
    }

    @GetMapping("/{usuarioId}/items")
    public ResponseEntity<List<ItemCarrito>> listarItems(@PathVariable Long usuarioId) {
        List<ItemCarrito> items = carritoService.listarItems(usuarioId);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long itemId) {
        carritoService.eliminarItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{usuarioId}/pagar")
    public ResponseEntity<String> pagarCarrito(@PathVariable Long usuarioId, @RequestParam Double monto) {
        try {
            carritoService.realizarPago(usuarioId, monto);
            return ResponseEntity.ok("Pago realizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar el pago: " + e.getMessage());
        }
    }
}
