package com.epac.vertical_inception.models;

import java.io.Serializable;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
    
    // Requisito para usar clases con JPA (Por el tema de la serializacion)
    private static final long serialVersionUID = 1L;

    // Ahora, anadimos los atributos de esta tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_usuario", nullable=false, unique=true)
    @Getter @Setter private Integer ID_usuario;

    @Column(name="nombre_usuario", length=100, nullable=false, unique=false)
    @Getter @Setter private String nombre_usuario;

    @Column(name="correo", length=100, nullable=false, unique=false)
    @Getter @Setter private String correo;

    // El tipo de esta columna es el tipo de un enumerado que hemos definido
    @Column(name="rol", nullable=false, unique=false, columnDefinition = "ENUM('Usuario', 'Administrador', 'Super-Administrador') NOT NULL")
    @Convert(converter = RolConverter.class)
    @Getter @Setter private Rol rol;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name="dummy_date", nullable=true, unique=false)
    @Getter @Setter private LocalDate dummy_date;

    // Porque estamos modelando una entidad que participa en una relacion muchos a muchos (n:m)
    // Como es una relacion n:m podemos (arbitrariamente) fijar esta entidad como la "propietaria" (owning entity of the relationship)
    @ManyToMany
    @JoinTable(
        name = "usuario_pertenece_a_unidad", 
        joinColumns = @JoinColumn(name = "ID_usuario"), 
        inverseJoinColumns = @JoinColumn(name = "ID_unidad"))
    @Getter Set<Unidad> unidadesALasQuePertenece =  new HashSet<>();
}
