package com.edhub.services.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MensajeDTO {
    
    private String remitente;
    private String destinatario;
    private String contenido;
}
