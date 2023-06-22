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
import com.edhub.models.Comentario;
import com.edhub.services.ComentarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comentario")
public class ComentarioController {
    
    private final ComentarioService comentarioService;

    @GetMapping
    public List<Comentario> findAll() {
        return comentarioService.obtenerTodos();
    }

    @PostMapping("/agregar-comentario")
    public Comentario agregarComentario(@RequestBody Comentario comentario) {
        return comentarioService.agregarComentario(comentario);
    }

    @PutMapping("/update-comentario/{id}")
    public Comentario updateComentario(@PathVariable Long id, @RequestBody Comentario comentario) {
        return comentarioService.actualizarComentario(id, comentario);
    }

    @DeleteMapping("/eliminar-comentario/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }




}
