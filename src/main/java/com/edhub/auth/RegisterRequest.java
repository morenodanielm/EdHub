package com.edhub.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// contendrá los datos que el usuario envía en la petición para registrarse al sistema
public class RegisterRequest {

    // atributos
    private String username;
    private String email;
    private String password;

}
