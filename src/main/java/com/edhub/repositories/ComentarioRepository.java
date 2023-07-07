package com.edhub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edhub.models.Comentario;
import com.edhub.models.Usuario;

// clase de acceso a datos para la entidad comentario
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
 
    List<Comentario> findAllByUsuario(Usuario usuario);
}
