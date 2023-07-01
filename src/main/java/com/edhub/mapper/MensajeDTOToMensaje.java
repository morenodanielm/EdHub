package com.edhub.mapper;

import org.springframework.stereotype.Component;
import com.edhub.models.*;
import com.edhub.models.Usuario;
import com.edhub.services.UsuarioService;
import com.edhub.services.dto.MensajeDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
// componente que convertirá un MensajeDTO en un objeto Mensaje
public class MensajeDTOToMensaje implements IMapper<MensajeDTO, Mensaje>{

    private final UsuarioService usuarioService;

    @Override
    public Mensaje map(MensajeDTO in) {
        Mensaje mensaje = new Mensaje();
        Usuario remitente = usuarioService.obtenerPorUsername(in.getRemitente());
        Usuario destinatario = usuarioService.obtenerPorUsername(in.getDestinatario());
        mensaje.setContenido(in.getContenido());
        mensaje.setUsuarioeRemitente(remitente);
        mensaje.setUsuarioDestinatario(destinatario);
        return mensaje;
    }
    
    
}
