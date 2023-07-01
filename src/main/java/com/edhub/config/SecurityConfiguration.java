package com.edhub.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import lombok.RequiredArgsConstructor;

// indica que es una clase que tiene métodos los cuales retornan beans, en este caso, con la configuración de seguridad
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// contendrá definición de seguridad de la API
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // bean el que contendrá toda la configuración de seguridad de la app, Spring al
    // lanzar la app, buscará ese bean y aplicará esa configuración
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> {
                    // se pasa una lista de las rutas o patrones a personalizar
                    authz.requestMatchers("/api/v1/auth/**", "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html")
                            // indica que se permitirán todas las rutas pasadas a requestMatchers(la lista blanca)
                            .permitAll()
                            // cualquier otra solicitud o ruta que no sea de la lista blanca, debe ser autenticada
                            .anyRequest()
                            .authenticated();
                })
                .headers((header) -> {
                    header.addHeaderWriter(
                            new StaticHeadersWriter("Access-Control-Allow-Origin", "http://localhost:3000"));
                })
                // se establece la política de session sin estado, es decir, no se creará un HttpSession
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // indicarle a Spring que proveedor de autenticación se quiere usar
                .authenticationProvider(authenticationProvider)
                // se define que el filtro de autenticación jwt se ejecutará antes del UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
