package com.epac.vertical_inception.services;

import java.util.List;

import com.epac.vertical_inception.models.Unidad;

public interface IUnidadService {

    // Definimos los metodos que estaran disponibles para interactuar con la tabla Unidad

    // Devuelve una lista con todas las unidades disponibles
    public List<Unidad> getAll();

    // Inserta una nueva unidad
    public Unidad save(Unidad unidad);

    // Devuelve una unidad dado un ID de unidad
    public Unidad getById(Integer id);

    // Elimina una unidad dado un ID de unidad
    public void remove(Integer id);

}
