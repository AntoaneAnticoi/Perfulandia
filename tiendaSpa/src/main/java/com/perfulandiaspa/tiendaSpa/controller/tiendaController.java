package com.perfulandiaspa.tiendaSpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.perfulandiaspa.tiendaSpa.model.tiendaModel;
import com.perfulandiaspa.tiendaSpa.services.tiendaServices;

@RestController
@RequestMapping("/api/tienda")
public class tiendaController {
    
    @Autowired
    private tiendaServices tiendServ;

    @GetMapping
    public List<tiendaModel> obtenerTiendas() {
        return tiendServ.obtenerTiendas();
    }

    @GetMapping("/{id}")
    public tiendaModel buscarTienda(@PathVariable int id) {
        return tiendServ.buscarTienda(id);
    }

    @PutMapping("/{id}")
    public boolean actualizarTienda(@PathVariable int id, @RequestBody tiendaModel tiendaUpdate) {
        tiendaUpdate.setId(id);
        return tiendServ.actualizarTienda(tiendaUpdate);
    }

    @DeleteMapping("/{id}")
    public boolean eliminarTienda(@PathVariable int id) {
        return tiendServ.eliminarTienda(id);
    }


}

