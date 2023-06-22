package com.edhub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edhub.models.Tutor;
import com.edhub.services.TutorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tutor")
public class TutorController {
    
    private final TutorService tutorService;
    
    public Tutor agregarTutor(@RequestBody Tutor tutor) {
        return tutorService.agregarTutor(tutor);
    }

    @GetMapping("/usuario-tutor/{id}")
    public Tutor usuarioATutor(@PathVariable Long id) {
        return tutorService.deUsuarioATutor(id);
    }
}
