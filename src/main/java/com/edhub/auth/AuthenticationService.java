package com.edhub.auth;

import com.edhub.config.JwtService;
import com.edhub.models.Role;
import com.edhub.models.Usuario;
import com.edhub.services.UsuarioService;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Qualifier()
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // construimos el objeto Usuario usando el patrón creacional builder(para tratar con objetos complejos)
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                // codifica contraseña para persistirla luego
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .fechaCreacion(LocalDateTime.now())
                // construye el objeto Usuario
                .build();

        usuarioService.agregarUsuario(usuario);
        // genera el token a partir del usuario(sin claims o privilegios extras)
        var jwtToken = jwtService.generateToken(usuario);
        // construye un objeto AuthenticationResponse con el token obtenido
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // autenticamos basado en el username(email en este caso) y la contraseña, retornará un objeto totalmente autenticado con credenciales
        // en caso de fallar lanza AuthenticationException, y la app lanzará un 403 forbidden
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        // obtenemos el usuario de la base de datos basado en el username del request 
        var user = usuarioService.obtenerPorUsername(request.getUsername());
        // genera el token a partir del usuario(sin claims o privilegios extras)
        var jwtToken = jwtService.generateToken(user);
        // construye un objeto AuthenticationResponse con el token obtenido
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
