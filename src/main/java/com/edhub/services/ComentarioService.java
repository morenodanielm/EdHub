package com.edhub.services;

import java.util.List;
import com.edhub.exceptions.EdhubExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.edhub.models.Comentario;
import com.edhub.repositories.ComentarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// clase de servicio para entidad comentario
public class ComentarioService {
    
    private final ComentarioRepository comentarioRepository;

    // agregará un comentario
    public Comentario agregarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    // obtendrá todos los comentarios
    public List<Comentario> obtenerTodos() {
        return comentarioRepository.findAll();
    }

    // actualizará un comentario
    public Comentario actualizarComentario(Comentario comentario) {
        if(comentario.getIdComentario() != null) {
            if (!comentarioRepository.existsById(comentario.getIdComentario())) {
                throw new EdhubExceptions("El comentario no existe", HttpStatus.NOT_FOUND);
            }
        }
        return comentarioRepository.save(comentario);
    }
    // eliminará un comentario
    public void eliminarComentario(Long id) {
        if(!comentarioRepository.existsById(id)) {
            throw new EdhubExceptions("El comentario no existe", HttpStatus.NOT_FOUND);
        }
        comentarioRepository.deleteById(id);
    }
    
    
}
