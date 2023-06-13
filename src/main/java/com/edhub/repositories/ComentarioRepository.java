package com.edhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edhub.models.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
    
}
