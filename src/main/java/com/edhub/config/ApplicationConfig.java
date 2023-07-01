package com.edhub.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;
import com.edhub.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
// contendrá configuración de seguridad
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

    // retorna un bean con la configuración cors, para permitir peticiones del cliente(front, navegador, etc)
    @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}


    
}
