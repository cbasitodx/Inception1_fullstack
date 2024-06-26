package com.epac.vertical_inception.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epac.vertical_inception.models.Unidad;
import com.epac.vertical_inception.repositories.UnidadRepository;

@Service
public class UnidadServiceImplementation implements IUnidadService {

    // Inyeccion de dependencias (asi no tenemos que instancias la clase)
    @Autowired
    UnidadRepository unidadrepository;

    // Para la implementacion vamos a usar metodos de JpaRepository
    @Override
    public List<Unidad> getAll() {
        return unidadrepository.findAll();
    }

    @Override
    public Unidad save(Unidad unidad) {
        return unidadrepository.save(unidad);
    }

    @Override
    public Unidad getById(Integer id) {
        return unidadrepository.findById(id).orElse(null);
    }

    @Override
    public void remove(Integer id) {
        unidadrepository.deleteById(id);
    }

}
