package com.epac.vertical_inception.controllers;

import java.util.HashMap;
import java.util.List;

import com.epac.vertical_inception.models.Usuario;

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

import com.epac.vertical_inception.services.UsuarioServiceImplementation;

@RestController
@RequestMapping("/epac/usuario")
@CrossOrigin
public class UsuarioController {

    // Inyeccion de dependencias
    @Autowired
    UsuarioServiceImplementation usuarioserviceimplementation;

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios() {
        return usuarioserviceimplementation.getAll();
    }
    
    @PostMapping("/save")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
        Usuario new_usuario = usuarioserviceimplementation.save(usuario);
        return new ResponseEntity<>(new_usuario, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
       Usuario usuarioPorId = usuarioserviceimplementation.getById(id);
       return ResponseEntity.ok(usuarioPorId); 
    }
    @PutMapping("/updateGivenId/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario usuarioPorId = usuarioserviceimplementation.getById(id);
        
        usuarioPorId.setNombre_usuario(usuario.getNombre_usuario());
        usuarioPorId.setCorreo(usuario.getCorreo());
        usuarioPorId.setRol(usuario.getRol());
        usuarioPorId.setDummy_date(usuario.getDummy_date());

        Usuario usuarioActualizado = usuarioserviceimplementation.save(usuarioPorId);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteGivenId/{id}")
    public ResponseEntity<HashMap<String, Boolean>> removeUsuario(@PathVariable Integer id) {
        usuarioserviceimplementation.remove(id);

        HashMap<String, Boolean> estadoUsuarioEliminado = new HashMap<>();
        estadoUsuarioEliminado.put("eliminado", true);
        return ResponseEntity.ok(estadoUsuarioEliminado);
    }

    @GetMapping("/getUnitsOfUserById/{id}")
    public List<String> getUnitsOfUserById(@PathVariable Integer id) {
        return usuarioserviceimplementation.getUnitsNameOfUserById(id);
    }
}   
