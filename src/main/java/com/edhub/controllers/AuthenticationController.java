package com.edhub.controllers;

import com.edhub.auth.AuthenticationRequest;
import com.edhub.auth.AuthenticationResponse;
import com.edhub.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edhub.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
// manejará todas las peticiones de registro e inicio de sesión
public class AuthenticationController {

    private final AuthenticationService service;
    
    // endPoint para registrar el usuario que viene en la petición y devolver el token asignado como respuesta
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registrar(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(service.register(registerRequest));
    }

    // endPoint para autenticar datos del usuario que viene en la petición y devolver un token como respuesta
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> autenticar(@RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok(service.authenticate(authRequest));
    }
}
