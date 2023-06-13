package com.edhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// indica que es una clase que tiene métodos los cuales retornan beans con la configuración de seguridad del aplicativo
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
 
    // bean el que contendrá toda la configuración de seguridad de la app, Spring al
    // lanzar la app, buscará ese bean y aplicará esa configuración
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         /*
         * CSRF (Cross-site request forgery) El CSRF es un tipo de exploit malicioso de
         * un sitio web en el que
         * comandos no autorizados son transmitidos por un usuario en el cual el sitio
         * web confía.
         * La razón por la que un ataque CSRF es posible es que la solicitud HTTP del
         * sitio web de la víctima y
         * la solicitud del sitio web del atacante son exactamente las mismas.
         */

        http
                .csrf((csrf) -> csrf.disable())
                // permite configurar que peticiones queremos autenticar y cuales no, por
                // ejemplo indicar que en el login y registro no
                // requerimos autenticación pero en las otras rutas si, o que todas las rutan
                // deben ser autenticadas
                .authorizeHttpRequests((authz) -> {
                    // se pasa una lista de las rutas o patrones a personalizar
                    authz.requestMatchers("/api/v1/auth/**")
                            // indica que se permitirán todas las rutas pasadas a requestMatchers(la lista
                            // blanca)
                            .permitAll()
                            // cualquier otra solicitud o ruta que no sea de la lista blanca, debe ser
                            // autenticada
                            .anyRequest()
                            .authenticated();
                })
                // al tener que autenticar cada solicitud que el filtro "atrape", no se debe
                // almacenar el estado de la session, debe ser sin estado.
                // hará que cada solicitud se autentique bien
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // indicarle a Spring que proveedor de autenticación se quiere usar
                .authenticationProvider(authenticationProvider)
                // se define que el filtro de autenticación jwt de ejecutará antes del
                // UsernamePasswordAuthenticationFilter
                // UsernamePasswordAuthenticationFilter procesa envio de formularios para su
                // autenticación(login)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
