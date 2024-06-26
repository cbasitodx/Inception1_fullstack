package com.epac.vertical_inception.models;

import java.io.Serializable;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
    
    // Requisito para usar clases con JPA (Por el tema de la serializacion)
    private static final long serialVersionUID = 1L;

    // Ahora, anadimos los atributos de esta tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_usuario", nullable=false, unique=true)
    private Integer ID_usuario;

    @Column(name="nombre_usuario", length=100, nullable=false, unique=false)
    private String nombre_usuario;

    @Column(name="correo", length=100, nullable=false, unique=false)
    private String correo;

    // El tipo de esta columna es el tipo de un enumerado que hemos definido
    @Column(name="rol", nullable=false, unique=false, columnDefinition = "ENUM('Usuario', 'Administrador', 'Super-Administrador') NOT NULL")
    @Convert(converter = RolConverter.class)
    private Rol rol;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name="dummy_date", nullable=true, unique=false)
    private LocalDate dummy_date;

    // Porque estamos modelando una entidad que participa en una relacion muchos a muchos (n:m)
    // Como es una relacion n:m podemos (arbitrariamente) fijar esta entidad como la "propietaria" (owning entity of the relationship)
    @ManyToMany
    @JoinTable(
        name = "usuario_pertenece_a_unidad", 
        joinColumns = @JoinColumn(name = "ID_usuario"), 
        inverseJoinColumns = @JoinColumn(name = "ID_unidad"))
    Set<Unidad> unidadesALasQuePertenece =  new HashSet<>();

    // Creamos un constructor vacio
    public Usuario() {

    }

    // Definimos los getters y setters

    // getters
    public Integer getID_usuario() { return this.ID_usuario; }

    public String getNombre_usuario() { return this.nombre_usuario; }
    
    public String getCorreo() { return this.correo; }
    
    public Rol getRol() { return this.rol; }

    public LocalDate getDummy_date() { return this.dummy_date; }

    // setters
    public void setID_usuario(Integer ID_usuario) { this.ID_usuario = ID_usuario; }

    public void setNombre_usuario(String nombre_usuario) { this.nombre_usuario = nombre_usuario; }
    
    public void setCorreo(String correo) { this.correo = correo; }
    
    public void setRol(Rol rol) { this.rol = rol; }
    
    public void setDummy_date(LocalDate dummy_date) { this.dummy_date = dummy_date; }

    public Set<Unidad> getUnidades() { return unidadesALasQuePertenece; }
}
