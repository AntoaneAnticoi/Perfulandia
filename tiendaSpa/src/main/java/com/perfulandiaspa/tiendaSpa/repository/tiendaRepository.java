package com.perfulandiaspa.tiendaSpa.repository;

import org.springframework.stereotype.Repository;
import com.perfulandiaspa.tiendaSpa.model.tiendaModel;

import java.util.ArrayList;
import java.util.List;

@Repository
public class tiendaRepository {

    private List<tiendaModel> listaTiendas = new ArrayList<>();

    public tiendaRepository(){
        listaTiendas.add(new tiendaModel(1, "PerfulandiaSPA", "Barrio Meiggs en Santiago",923568952 ,"perfulandispa@gmail.com"));
    } 

    public tiendaRepository (List<tiendaModel> listarTiendas) {
        this.listaTiendas = listarTiendas;
    } 
    
    public List<tiendaModel> obtenerTiendas() {
        return listaTiendas;
    }

    public tiendaModel buscarPorId(int id) {
        for (tiendaModel tienda : listaTiendas) {
            if (tienda.getId() == id) {
                return tienda;
            }
        }
        return null;
    }   

    public boolean eliminarPorId(int id) {
        for (int i = 0; i < listaTiendas.size(); i++) {
            if (listaTiendas.get(i).getId() == id) {
                listaTiendas.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean actualizarTienda(tiendaModel tienda) {
      for (int i = 0; i < listaTiendas.size(); i++) {
            if (listaTiendas.get(i).getId() == tienda.getId()) {
                listaTiendas.set(i, tienda);
                return true;
            }
        }
        return false;
    }
}
