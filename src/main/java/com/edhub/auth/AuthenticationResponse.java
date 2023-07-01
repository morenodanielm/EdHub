package com.edhub.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// se le retornará el objeto de la clase con el token al cliente cuando se registre o autentique
public class AuthenticationResponse {

    // atributos
    private String token;
}
