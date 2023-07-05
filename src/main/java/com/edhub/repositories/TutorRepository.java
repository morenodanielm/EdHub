package com.edhub.repositories;

import com.edhub.models.Especialidad;
import com.edhub.models.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.EnumSet;
import java.util.List;

// clase de acceso a datos para la entidad tutor
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    // obtener todos los tutores por una especialidad
    List<Tutor> findAllByEspecialidadesIn(EnumSet<Especialidad> especialidad);

    // validar si existe un tutor por especialidad
    boolean existsByEspecialidadesIn(EnumSet<Especialidad> especialidad);
}
