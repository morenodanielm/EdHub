package com.edhub.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edhub.models.*;

// clase de acceso a datos para la entidad calificacion
public interface CalificacionRepository extends JpaRepository<Calificacion, Long>{

    // obtener una calificación por usuario calificado o calificador
    Optional<Calificacion> findByCalificado(Usuario calificado);

    Optional<Calificacion> findByCalificador(Usuario calificador);

    // obtener todas las calificaciones por usuario calificado o calificador
    List<Calificacion> findAllByCalificado(Usuario calificado);
    
    List<Calificacion> findAllByCalificador(Usuario calificador);
    
}
