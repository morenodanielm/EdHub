package com.edhub.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // registrará los datos de la petición
    private String username;
    private String email;
    private String password;

}
