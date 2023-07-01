package com.edhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edhub.models.Mensaje;

// clase de acceso a datos para la entidad mensajes
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{

    
}
