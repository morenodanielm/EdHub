package com.edhub.mapper;

import org.springframework.stereotype.Component;

import com.edhub.models.Calificacion;
import com.edhub.models.Comentario;
import com.edhub.models.Usuario;
import com.edhub.services.UsuarioService;
import com.edhub.services.dto.ComentarioDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ComentarioDTOToComentario implements IMapper<ComentarioDTO, Comentario>{

    private final UsuarioService usuarioService;

    @Override
    public Comentario map(ComentarioDTO in) {
        Comentario comentario = new Comentario();
        comentario.setComentario(in.getComentario());
        Usuario calificado = usuarioService.obtenerPorUsername(in.getUsernameCalificado());
        Usuario calificador = usuarioService.obtenerPorUsername(in.getUsernameCalificador());
        comentario.setUsuario(calificador);
        Calificacion c = new Calificacion();
        c.setCalificado(calificado);
        c.setCalificador(calificador);
        c.setCalificacion(4);
        comentario.setCalificacion(c);
        return comentario;
    }
    
}
