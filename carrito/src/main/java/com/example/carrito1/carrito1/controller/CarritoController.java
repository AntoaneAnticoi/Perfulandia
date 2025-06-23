package com.example.carrito1.carrito1.controller;

import com.example.carrito1.carrito1.model.Carrito;
import com.example.carrito1.carrito1.model.ItemCarrito;
import com.example.carrito1.carrito1.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long usuarioId) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/{usuarioId}/agregar")
    public ResponseEntity<ItemCarrito> agregarProducto(
            @PathVariable Long usuarioId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {

        ItemCarrito item = carritoService.agregarProducto(usuarioId, productoId, cantidad);
        return ResponseEntity.ok(item);
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
