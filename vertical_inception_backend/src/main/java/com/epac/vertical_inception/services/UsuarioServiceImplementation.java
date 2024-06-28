package com.epac.vertical_inception.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epac.vertical_inception.models.Unidad;
import com.epac.vertical_inception.models.Usuario;
import com.epac.vertical_inception.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImplementation implements IUsuarioService {

    // Inyeccion de dependencias (asi no tenemos que instancias la clase)
    @Autowired
    UsuarioRepository usuariorepository;

    // Para la implementacion vamos a usar metodos de JpaRepository

    @Override
    public List<Usuario> getAll() {
        return usuariorepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuariorepository.save(usuario);
    }

    @Override
    public Usuario getById(Integer id) {
        return usuariorepository.findById(id).orElse(null);
    }

    @Override
    public void remove(Integer id) {
        usuariorepository.deleteById(id);
    }

    // Explicacion: usuariorepository.getUnitsById(id).stream() devuelve un Stream de objetos de tipo Unidad.
    //              Cada unidad la mapeamos a su nombre (.map(Unidad :: getNombre_unidad))
    //              Luego, almacenamos todo en una lista (.collect(Collectors.toList()))
    @Override
    public List<String> getUnitsNameOfUserById(Integer id) {
        return usuariorepository.getUnitsById(id).stream()
            .map(Unidad :: getNombre_unidad)
            .collect(Collectors.toList());
    }
}
