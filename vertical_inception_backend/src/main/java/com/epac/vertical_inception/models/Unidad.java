package com.epac.vertical_inception.models;

import java.io.Serializable;

import jakarta.persistence.*;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="unidad")
public class Unidad implements Serializable{
    
    // Requisito para usar clases con JPA (Por el tema de la serializacion)
    private static final long serialVersionUID = 1L;

    // Ahora, anadimos los atributos de esta tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_unidad", nullable=false, unique=true)
    private Integer ID_unidad;

    @Column(name="nombre_unidad", length=100, nullable=false, unique=false)
    private String nombre_unidad;

    // Porque estamos modelando una entidad que participa en una relacion muchos a muchos (n:m)
    // Esta es la entidad "no propietaria"
    @ManyToMany(mappedBy = "unidadesALasQuePertenece") // Este es el Set en la entidad Usuario
    Set<Usuario> usuariosQuePertenecenAEstaUnidad = new HashSet<>();

    // Creamos un constructor vacio
    public Unidad() {

    }

    // Definimos los getters y setters

    // getters
    public Integer getID_unidad() { return this.ID_unidad; }

    public String getNombre_unidad() { return this.nombre_unidad; }

    // setters
    public void setID_usuario(Integer ID_unidad) { this.ID_unidad = ID_unidad; }

    public void setNombre_unidad(String nombre_unidad) { this.nombre_unidad = nombre_unidad; }
    

}