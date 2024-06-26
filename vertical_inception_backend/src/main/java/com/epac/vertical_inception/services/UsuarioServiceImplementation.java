package com.epac.vertical_inception.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
