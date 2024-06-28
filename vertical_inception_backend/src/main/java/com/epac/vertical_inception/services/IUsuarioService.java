package com.epac.vertical_inception.services;

import java.util.List;

import com.epac.vertical_inception.models.Usuario;

public interface IUsuarioService {

    // Definimos los metodos que estaran disponibles para interactuar con la tabla Usuario

    // Devuelve una lista con todos los usuarios
    public List<Usuario> getAll();

    // Inserta un nuevo usuario
    public Usuario save(Usuario usuario);

    // Devuelve un usuario dado un ID de usuario
    public Usuario getById(Integer id);

    // Elimina un usuario dado un ID de usuario
    public void remove(Integer id);

    // Devuelve una lista con los NOMBRES de las unidades de un usuario dado su ID
    public List<String> getUnitsNameOfUserById(Integer id); 
}
