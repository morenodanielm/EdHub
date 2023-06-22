package com.edhub.services;

import java.time.LocalDateTime;
import java.util.List;

import com.edhub.exceptions.EdhubExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.edhub.models.Comentario;
import com.edhub.repositories.ComentarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComentarioService {
    
    private final ComentarioRepository comentarioRepository;

    public Comentario agregarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public List<Comentario> obtenerTodos() {
        return comentarioRepository.findAll();
    }

    public Comentario actualizarComentario(Long id, Comentario comentario) {
        if(!comentarioRepository.existsById(id)) {
            throw new EdhubExceptions("El comentario no existe", HttpStatus.NOT_FOUND);
        }
        return comentarioRepository.save(comentario);
    }
    public void eliminarComentario(Long id) {
        if(!comentarioRepository.existsById(id)) {
            throw new EdhubExceptions("El comentario no existe", HttpStatus.NOT_FOUND);
        }
        comentarioRepository.deleteById(id);
    }
    
    
}
