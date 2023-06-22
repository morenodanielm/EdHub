package com.edhub.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.edhub.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository usuarioRepository;

     // retorna el bean UserDetailsService para registrarlo en el contenedor de Spring e inyectarlo donde sea requerido
    @Bean
    public UserDetailsService userDetailsService() {
        // pasamos el username(o email) al repositorio y obtenemos un Optional<User>
        return username -> usuarioRepository.findByUsername(username)
        // se obtiene el User si está presente, si no se lanza una exception usando expresión la lambda tipo Supplier
        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

     // retorna el bean que es el proveedor de autenticación
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // DaoAuthenticationProvider es una implementación de AuthenticationProvider que aprovecha UserDetailsService y PasswordEncoder 
        // para autenticar un nombre de usuario y una contraseña
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // retorna un bean AuthenticationManager que es un contenedor para proveedores de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // retorna un bean del tipo PasswordEncoder, usado para la codificación de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    
}
