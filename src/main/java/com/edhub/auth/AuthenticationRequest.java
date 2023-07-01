package com.edhub.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// contendrá datos de la petición para la autenticación de un usuario
public class AuthenticationRequest {

    // atributos
    private String username;
    private String password;

}
