package com.edhub.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edhub.models.Calificacion;
import com.edhub.models.Usuario;
import com.edhub.services.CalificacionService;
import com.edhub.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/calificacion")
// manejará todas las peticiones para las calificaciones de usuarios
public class CalificacionController {
    
    private final CalificacionService calificacionService;
    private final UsuarioService usuarioService;

    // retornará todas las calificaciones realizadas a un usuario en específico
    @GetMapping("/calificado/{id}")
    public List<Calificacion> ObtenerTodosPorUsuarioCalificado(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return calificacionService.obtenerTodasPorCalificado(usuario);
    }

    // retornará todas las calificaciones realizadas por un usuario en específico
    @GetMapping("/calificador/{id}")
    public List<Calificacion> ObtenerTodosPorUsuarioCalificador(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return calificacionService.obtenerTodasPorCalificador(usuario);
    }

    // enviará a la capa service una calificación realizada
    @PostMapping("/agregar-calificacion")
    public Calificacion agregarCalificacion(@RequestBody Calificacion calificacion) {
        return calificacionService.agregarCalificacion(calificacion);
    }

    // enviará a la capa service una calificación que quiere ser actualizada
    @PutMapping("/update-calificacion/{id}")
    public ResponseEntity<Void> actualizarCalificacion(@PathVariable Long id, @RequestBody Calificacion calificacion) {
        calificacionService.actualizarCalificacion(id, calificacion);
        return ResponseEntity.ok().build();
    }

    // enviará a la capa service el id de una calificación para ser eliminada
    @DeleteMapping("/eliminar-calificacion/{id}")
    public ResponseEntity<Void> eliminarCalificacion(@PathVariable Long id) {
        calificacionService.eliminarCalificacion(id);
        return ResponseEntity.noContent().build();
    }
}