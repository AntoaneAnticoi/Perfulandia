package com.example.carrito1.carrito1.service;

import com.example.carrito1.carrito1.model.Carrito;
import com.example.carrito1.carrito1.model.ItemCarrito;
import com.example.carrito1.carrito1.repository.CarritoRepository;
import com.example.carrito1.carrito1.repository.ItemCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ItemCarritoRepository itemCarritoRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CarritoService(CarritoRepository carritoRepository,
                          ItemCarritoRepository itemCarritoRepository,
                          RestTemplate restTemplate) {
        this.carritoRepository = carritoRepository;
        this.itemCarritoRepository = itemCarritoRepository;
        this.restTemplate = restTemplate;
    }

    public Carrito obtenerCarritoPorUsuario(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId).orElseGet(() -> {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuarioId(usuarioId);
            return carritoRepository.save(nuevoCarrito);
        });
    }

    public ItemCarrito agregarProducto(Long usuarioId, Long productoId, int cantidad) {
        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);

        ItemCarrito item = new ItemCarrito();
        item.setProductoId(productoId);
        item.setCantidad(cantidad);
        item.setCarrito(carrito);

        return itemCarritoRepository.save(item);
    }

    public List<ItemCarrito> listarItems(Long usuarioId) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(usuarioId);
        if (carritoOpt.isPresent()) {
            Long carritoId = carritoOpt.get().getId();
            return itemCarritoRepository.findByCarrito_Id(carritoId);
        }
        return List.of();
    }

    public void eliminarItem(Long itemId) {
        itemCarritoRepository.deleteById(itemId);
    }

    public void vaciarCarrito(Long usuarioId) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(usuarioId);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            List<ItemCarrito> items = itemCarritoRepository.findByCarrito_Id(carrito.getId());
            itemCarritoRepository.deleteAll(items);
        }
    }

    public void realizarPago(Long usuarioId, Double monto) {
        String url = "http://localhost:8082/api/pagos"; // Asegúrate que el microservicio de pagos está en este puerto

        Map<String, Object> request = new HashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("monto", monto);
        request.put("metodoPago", "tarjeta");
        request.put("estado", "pendiente");

        restTemplate.postForObject(url, request, String.class);
    }
}
