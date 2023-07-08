package com.edhub.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.edhub.models.*;
import com.edhub.services.TutorService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tutor")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TutorController {
    
    private final TutorService tutorService;

    // agregará un nuevo tutor
    @PostMapping("/agregar-tutor")
    public Tutor agregarTutor(@RequestBody Tutor tutor) {
        return tutorService.agregarTutor(tutor);
    }

    // actualizará un tutor existente
    @PutMapping("/agregar-tutor")
    public Tutor actualizarTutor(@RequestBody Tutor tutor) {
        return tutorService.agregarTutor(tutor);
    }

    // convertirá a un usuario normal en un tutor
    @GetMapping("/usuario-tutor/{id}")
    public Tutor usuarioATutor(@PathVariable Long id, @RequestBody Especialidad[] especialidades) {
        return tutorService.deUsuarioATutor(id, especialidades);
    }

    // retornará todos los tutores
    @GetMapping
    public List<Tutor> obtenerTodos() {
    	return tutorService.obtenerTodos();
    }
}
