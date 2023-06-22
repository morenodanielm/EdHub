package com.edhub.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    // se le retornará al cliente cuando se registre o autentique
    private String token;
}
