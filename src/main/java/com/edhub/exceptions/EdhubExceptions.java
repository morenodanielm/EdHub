package com.edhub.exceptions;

import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
// clase exception personalizada que se arrojará cuando ocurra un error en la API
public class EdhubExceptions extends RuntimeException {

    private String mensaje;
    private HttpStatus httpStatus; 

    public EdhubExceptions(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.mensaje = mensaje;
        this.httpStatus = httpStatus;
    }
}
