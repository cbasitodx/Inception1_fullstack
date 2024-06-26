package com.epac.vertical_inception.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epac.vertical_inception.models.Unidad;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Integer> {

}
