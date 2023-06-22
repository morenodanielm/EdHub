package com.edhub.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edhub.models.Calificacion;
import com.edhub.models.Usuario;
import com.edhub.services.CalificacionService;
import com.edhub.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/calificacion")
public class CalificacionController {
    
    private final CalificacionService calificacionService;
    private final UsuarioService usuarioService;

    @GetMapping("/calificado/{id}")
    public List<Calificacion> findAllByUsuarioCalificado(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return calificacionService.obtenerTodasPorCalificado(usuario);
    }

    @GetMapping("/calificador/{id}")
    public List<Calificacion> findAllByUsuarioCalificador(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return calificacionService.obtenerTodasPorCalificador(usuario);
    }

    @PostMapping("/agregar-calificacion")
    public Calificacion agregarCalificacion(@RequestBody Calificacion calificacion) {
        return calificacionService.agregarCalificacion(calificacion);
    }

    @PutMapping("/update-calificacion/{id}")
    public ResponseEntity<Void> updateCalificacion(@PathVariable Long id, @RequestBody Calificacion calificacion) {
        calificacionService.actualizarCalificacion(id, calificacion);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar-calificacion/{id}")
    public ResponseEntity<Void> eliminarCalificacion(@PathVariable Long id) {
        calificacionService.eliminarCalificacion(id);
        return ResponseEntity.noContent().build();
    }
}