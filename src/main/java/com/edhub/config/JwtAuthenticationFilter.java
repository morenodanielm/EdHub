package com.edhub.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        // obtener el token de autorización del header del request
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        // si el token es nulo o no inicia con "Bearer" se invoca el siguiente filtro en
        // la cadena
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // obtenemos el token sin la cadena "Bearer"
        jwtToken = authHeader.substring(7);
        // obtenemos el username(puede ser username o email) del jwtService
        username = jwtService.extractUsername(jwtToken);
        // si getAuthentication retorna null, quiere decir que el usuario no está
        // autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // obtenemos el usuario con el UserDetailsService(el lo obtendrá de la base de
            // datos)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // validar que el token siga vigente
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // este objeto es una simple representación del username y password que se crea
                // con fin de poder actualizar el
                // SecurityContextHolder(contexto de seguridad de Spring) y autenticar el
                // usuario
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                // establecer detalles(para reforzar o ampliar el token) con detalles(valga la
                // redundancia) del objeto request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                // actualizar el SecurityContextHolder(autenticar al usuario)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // invoca el siguiente filtro en la cadena(hacerlo siempre que se trabaje con filtros)
        filterChain.doFilter(request, response);
    }

}
