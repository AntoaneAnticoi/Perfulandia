package com.example.carrito1.carrito1.service;

import com.example.carrito1.carrito1.model.Carrito;
import com.example.carrito1.carrito1.model.ItemCarrito;
import com.example.carrito1.carrito1.repository.CarritoRepository;
import com.example.carrito1.carrito1.repository.ItemCarritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ItemCarritoRepository itemCarritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private Carrito carrito;

    @BeforeEach
    void setUp() {
        carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuarioId(123L);
    }

    @Test
    void testObtenerCarritoPorUsuario_Existente() {
        when(carritoRepository.findByUsuarioId(123L)).thenReturn(Optional.of(carrito));

        Carrito result = carritoService.obtenerCarritoPorUsuario(123L);

        assertNotNull(result);
        assertEquals(123L, result.getUsuarioId());
        verify(carritoRepository, times(1)).findByUsuarioId(123L);
    }

    @Test
    void testObtenerCarritoPorUsuario_NoExistente() {
        when(carritoRepository.findByUsuarioId(123L)).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Carrito result = carritoService.obtenerCarritoPorUsuario(123L);

        assertNotNull(result);
        assertEquals(123L, result.getUsuarioId());
        verify(carritoRepository).save(any(Carrito.class));
    }

    @Test
    void testAgregarProducto() {
        when(carritoRepository.findByUsuarioId(123L)).thenReturn(Optional.of(carrito));
        when(itemCarritoRepository.save(any(ItemCarrito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ItemCarrito item = carritoService.agregarProducto(123L, 99L, 2);

        assertNotNull(item);
        assertEquals(99L, item.getProductoId());
        assertEquals(2, item.getCantidad());
        assertEquals(carrito, item.getCarrito());
    }

    @Test
    void testListarItems_ConCarrito() {
        when(carritoRepository.findByUsuarioId(123L)).thenReturn(Optional.of(carrito));
        when(itemCarritoRepository.findByCarrito_Id(1L)).thenReturn(List.of(new ItemCarrito(), new ItemCarrito()));

        List<ItemCarrito> items = carritoService.listarItems(123L);

        assertEquals(2, items.size());
    }

    @Test
    void testListarItems_SinCarrito() {
        when(carritoRepository.findByUsuarioId(123L)).thenReturn(Optional.empty());

        List<ItemCarrito> items = carritoService.listarItems(123L);

        assertTrue(items.isEmpty());
    }

    @Test
    void testEliminarItem() {
        carritoService.eliminarItem(5L);
        verify(itemCarritoRepository).deleteById(5L);
    }

    @Test
    void testVaciarCarrito() {
        when(carritoRepository.findByUsuarioId(123L)).thenReturn(Optional.of(carrito));
        when(itemCarritoRepository.findByCarrito_Id(1L)).thenReturn(List.of(new ItemCarrito(), new ItemCarrito()));

        carritoService.vaciarCarrito(123L);

        verify(itemCarritoRepository).deleteAll(anyList());
    }
}
