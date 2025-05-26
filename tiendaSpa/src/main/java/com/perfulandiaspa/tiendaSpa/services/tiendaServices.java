package com.perfulandiaspa.tiendaSpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandiaspa.tiendaSpa.model.tiendaModel;
import com.perfulandiaspa.tiendaSpa.repository.tiendaRepository;

import java.util.List;

@Service
public class tiendaServices {

    @Autowired
    private tiendaRepository tiendaRepo;

    public tiendaServices (tiendaRepository tiendaRepo) {
        this.tiendaRepo = tiendaRepo;
    }

    public List<tiendaModel> obtenerTiendas() {
        return tiendaRepo.obtenerTiendas();
    }

    public tiendaModel buscarTienda(int id) {
        return tiendaRepo.buscarPorId(id);
    }

    public boolean eliminarTienda(int id) {
        return tiendaRepo.eliminarPorId(id);
    }

    public boolean actualizarTienda(tiendaModel tiendaUpdate) {
        return tiendaRepo.actualizarTienda(tiendaUpdate);
    }

    
    


}
