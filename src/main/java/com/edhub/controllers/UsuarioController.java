package com.edhub.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edhub.models.Usuario;
import com.edhub.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // retornará todos los usuarios
    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }

    // retornará un usuario en específico
    @GetMapping("/findByUsername/{username}")
    public Usuario obtenerPorUsername(@PathVariable String username) {
        return usuarioService.obtenerPorUsername(username);
    }

    // actualizará un usuario
    @PutMapping("/update-user/{id}")
    public ResponseEntity<Void> actualizarUsuario(@RequestBody Usuario usuario) {
        usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    // eliminará un usuario
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
