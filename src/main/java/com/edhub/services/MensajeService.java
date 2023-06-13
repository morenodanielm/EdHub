package com.edhub.services;

import java.util.List;

import com.edhub.exceptions.EdhubExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.edhub.models.Mensaje;
import com.edhub.repositories.MensajeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MensajeService {

    private final MensajeRepository mensajeRepository;

    public void agregarMensaje(Mensaje mensaje)  {
        mensajeRepository.save(mensaje);    
    }

    public List<Mensaje> obtenerTodos() {
        return mensajeRepository.findAll();
    }

    public void eliminarMensaje(Mensaje mensaje) {
        if(!mensajeRepository.existsById(mensaje.getIdMensaje())) {
            throw new EdhubExceptions("El mensaje no existe", HttpStatus.NOT_FOUND);
        }
        mensajeRepository.delete(mensaje);
    }
}
