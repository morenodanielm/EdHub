package com.edhub.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edhub.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);
    
}
