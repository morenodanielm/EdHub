package com.edhub.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edhub.models.Usuario;

// clase de acceso a datos para la entidad usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    // obtener un usuario por el username
    Optional<Usuario> findByUsername(String username);

    // validar si existe un usuario con un determinado username
    boolean existsByUsername(String username);

    // busca un usuario por email
    Optional<Usuario> findByEmail(String email);
    
}
