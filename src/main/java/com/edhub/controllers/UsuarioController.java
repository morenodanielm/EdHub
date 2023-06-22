package com.edhub.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edhub.models.Usuario;
import com.edhub.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    
    @GetMapping
    public List<Usuario> findAll() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/findByUsername/{username}")
    public Usuario findByUsername(@PathVariable String username) {
        return usuarioService.obtenerPorUsername(username);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
