package com.edhub.services;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.Calificacion;
import com.edhub.models.Usuario;
import com.edhub.repositories.CalificacionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;

    public Calificacion agregarCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    public List<Calificacion> obtenerTodas() {
        return calificacionRepository.findAll();
    }

    public Calificacion obtenerPorUsuarioCalificado(Usuario usuarioCalificado) {
        return calificacionRepository.findByCalificado(usuarioCalificado)
            .orElseThrow(() -> new EdhubExceptions("El usuario no tiene calificaciones", HttpStatus.NOT_FOUND));
    }

    public Calificacion obtenerPorUsuarioCalificador(Usuario usuarioCalificador) {
        return calificacionRepository.findByCalificador(usuarioCalificador)
            .orElseThrow(() -> new EdhubExceptions("El usuario no ha realizado calificaciones", HttpStatus.NOT_FOUND));
    }

    public List<Calificacion> obtenerTodasPorCalificado(Usuario calificado) {
        return calificacionRepository.findAllByCalificado(calificado);
    }

    public List<Calificacion> obtenerTodasPorCalificador(Usuario calificador) {
        return calificacionRepository.findAllByCalificador(calificador);
    }

    public void actualizarCalificacion(Long id, Calificacion calificacion) {
        if(!calificacionRepository.existsById(id)) {
            throw new EdhubExceptions("No existe la calificación", HttpStatus.NOT_FOUND);
        }
        calificacionRepository.save(calificacion);
    }

    public void eliminarCalificacion(Long id) {
        if(!calificacionRepository.existsById(id)) {
            throw new EdhubExceptions("No existe la calificación", HttpStatus.NOT_FOUND);
        }
        calificacionRepository.deleteById(id);
    }
}
