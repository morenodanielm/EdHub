package com.edhub.repositories;

import com.edhub.models.Especialidad;
import com.edhub.models.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    List<Tutor> findAllByEspecialidad(Especialidad especialidad);

    boolean existsByEspecialidad(Especialidad especialidad);
}
