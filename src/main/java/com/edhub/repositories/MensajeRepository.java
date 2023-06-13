package com.edhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edhub.models.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long>{

    
}
