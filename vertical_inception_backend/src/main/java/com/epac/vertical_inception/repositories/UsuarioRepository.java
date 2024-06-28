package com.epac.vertical_inception.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epac.vertical_inception.models.Unidad;
import com.epac.vertical_inception.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Devuelve una lista con las unidades de un usuario dado su ID
    @Query(value = "SELECT u.unidadesALasQuePertenece FROM Usuario u WHERE u.ID_usuario = ?1")
    public Set<Unidad> getUnitsById(Integer id); 

}
