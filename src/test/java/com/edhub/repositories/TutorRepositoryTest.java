package com.edhub.repositories;

import com.edhub.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
// clase test para tutor repository
class TutorRepositoryTest {

    @Autowired
    // objeto bajo test
    private TutorRepository underTest;

    @Mock
    private Tutor tutor;

    @BeforeEach
    void setUp() {
        // para persistir un tutor, extrae el usuario, y mapealo a un tutor, luego persiste el tutor.
        tutor = new Tutor();
        tutor.setUsername("daniel21");
        tutor.setEmail("daniel@mail.com");
        tutor.setPassword("12345678");
        tutor.setRole(Role.ROLE_TUTOR);
        tutor.setFechaCreacion(LocalDateTime.now());
        tutor.setIdUsuario(1L);
        tutor.setDisponible(true);
        tutor.setEspecialidad(EnumSet.of(Especialidad.JAVA));
    }

    // test para verficar que se puede obtener todos los tutores con cierta especialidad
    @Test
    void itShouldFindAllByEspecialidad() {
        // given
        underTest.save(tutor);

        // when
        List<Tutor> allByEspecialidad = underTest.findAllByEspecialidad(Especialidad.JAVA);

        // then
        assertThat(allByEspecialidad.size()).isEqualTo(1);
    }

    // test para verficar que se puede validar la existencia de un tutor con cierta especialidad
    @Test
    void itShouldFindByEspecialidad() {
        // given
        underTest.save(tutor);

        // when
        boolean b = underTest.existsByEspecialidad(Especialidad.JAVA);

        // then
        assertThat(b).isTrue();

    }
}