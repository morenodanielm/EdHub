package com.edhub.services.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
// representará un objeto Mensaje el cuál no contiene todos los atributos del original
public class MensajeDTO {

    // atributos
    private String remitente;
    private String destinatario;
    private String contenido;
}
