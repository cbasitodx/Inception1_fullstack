package com.epac.vertical_inception.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.HashSet;

@NoArgsConstructor
@Entity
@Table(name="unidad")
public class Unidad implements Serializable{
    
    // Requisito para usar clases con JPA (Por el tema de la serializacion)
    private static final long serialVersionUID = 1L;

    // Ahora, anadimos los atributos de esta tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_unidad", nullable=false, unique=true)
    @Getter @Setter private Integer ID_unidad;

    @Column(name="nombre_unidad", length=100, nullable=false, unique=false)
    @Getter @Setter private String nombre_unidad;

    // Porque estamos modelando una entidad que participa en una relacion muchos a muchos (n:m)
    // Esta es la entidad "no propietaria"
    @ManyToMany(mappedBy = "unidadesALasQuePertenece") // Este es el Set en la entidad Usuario
    Set<Usuario> usuariosQuePertenecenAEstaUnidad = new HashSet<>();
}