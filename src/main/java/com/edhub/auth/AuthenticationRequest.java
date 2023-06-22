package com.edhub.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    // guarda datos de la petición para la autenticación de un usuario
    private String username;
    private String password;

}
