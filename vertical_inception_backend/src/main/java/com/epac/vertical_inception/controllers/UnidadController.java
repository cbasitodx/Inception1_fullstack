package com.epac.vertical_inception.controllers;

import java.util.HashMap;
import java.util.List;

import com.epac.vertical_inception.models.Unidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epac.vertical_inception.services.UnidadServiceImplementation;

@RestController
@RequestMapping("/epac/unidad")
@CrossOrigin
public class UnidadController {

    // Inyeccion de dependencias
    @Autowired
    UnidadServiceImplementation unidadserviceimplementation;

    @GetMapping("/unidades")
    public List<Unidad> getUnidades() {
        return unidadserviceimplementation.getAll();
    }
    
    @PostMapping("/save")
    public ResponseEntity<Unidad> saveUnidad(@RequestBody Unidad unidad) {
        Unidad new_unidad = unidadserviceimplementation.save(unidad);
        return new ResponseEntity<>(new_unidad, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Unidad> getUnidadById(@PathVariable Integer id) {
       Unidad unidadPorId = unidadserviceimplementation.getById(id);
       return ResponseEntity.ok(unidadPorId); 
    }
    
    @PutMapping("/updateGivenId/{id}")
    public ResponseEntity<Unidad> updateUnidad(@PathVariable Integer id, @RequestBody Unidad unidad) {
        Unidad unidadPorId = unidadserviceimplementation.getById(id);
        
        unidadPorId.setNombre_unidad(unidad.getNombre_unidad());

        Unidad unidadActualizada = unidadserviceimplementation.save(unidadPorId);
        return new ResponseEntity<>(unidadActualizada, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteGivenId/{id}")
    public ResponseEntity<HashMap<String, Boolean>> removeUnidad(@PathVariable Integer id) {
        unidadserviceimplementation.remove(id);

        HashMap<String, Boolean> estadoUnidadEliminada = new HashMap<>();
        estadoUnidadEliminada.put("eliminado", true);
        return ResponseEntity.ok(estadoUnidadEliminada);
    }

}
