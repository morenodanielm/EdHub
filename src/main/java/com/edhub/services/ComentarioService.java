package com.edhub.services;

import java.util.List;
import com.edhub.exceptions.EdhubExceptions;
import com.edhub.mapper.ComentarioDTOToComentario;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.edhub.models.Comentario;
import com.edhub.models.Usuario;
import com.edhub.repositories.ComentarioRepository;
import com.edhub.services.dto.ComentarioDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// clase de servicio para entidad comentario
public class ComentarioService {
    
    private final ComentarioRepository comentarioRepository;
    private final ComentarioDTOToComentario mapper;

    // agregará un comentario
    public Comentario agregarComentario(ComentarioDTO comentarioDTO) {
        Comentario comentario = mapper.map(comentarioDTO);
        return comentarioRepository.save(comentario);
    }

    // obtendrá todos los comentarios
    public List<Comentario> obtenerTodos() {
        return comentarioRepository.findAll();
    }

    // obtendrá todos los comentarios de un usuario
    public List<Comentario> obtenerTodosPorUsuario(Usuario usuario) {
        return comentarioRepository.findAllByUsuario(usuario);
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
