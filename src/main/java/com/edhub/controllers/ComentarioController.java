package com.edhub.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edhub.models.Comentario;
import com.edhub.services.ComentarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comentario")
// recibirá todas las peticiones sobre los comentarios
public class ComentarioController {
    
    private final ComentarioService comentarioService;

    // obtener todos los comentarios
    @GetMapping
    public List<Comentario> obtenerTodos() {
        return comentarioService.obtenerTodos();
    }

    // recibe un comentario para persistirlo
    @PostMapping("/agregar-comentario")
    public Comentario agregarComentario(@RequestBody Comentario comentario) {
        return comentarioService.agregarComentario(comentario);
    }

    // recibe un comentario para actualizarlo
    @PutMapping("/update-comentario")
    public Comentario actualizarComentario(@RequestBody Comentario comentario) {
        return comentarioService.actualizarComentario(comentario);
    }

    // recibe el id de un comentario para eliminarlo
    @DeleteMapping("/eliminar-comentario/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

}
