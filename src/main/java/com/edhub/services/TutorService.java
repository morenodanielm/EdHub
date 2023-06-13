package com.edhub.services;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.Especialidad;
import com.edhub.models.Tutor;
import com.edhub.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;

    public List<Tutor> obtenerTodoPorEspecialidad(Especialidad especialidad) {
        if(!tutorRepository.existsByEspecialidad(especialidad)) {
            throw new EdhubExceptions("Especialidad " + especialidad + " no hallada", HttpStatus.NOT_FOUND);
        }
        return tutorRepository.findAllByEspecialidad(especialidad);
    }
}
