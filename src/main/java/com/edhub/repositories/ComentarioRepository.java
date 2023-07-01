package com.edhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edhub.models.Comentario;

// clase de acceso a datos para la entidad comentario
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
    
}
