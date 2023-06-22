package com.edhub.services;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.Especialidad;
import com.edhub.models.Role;
import com.edhub.models.Tutor;
import com.edhub.models.Usuario;
import com.edhub.repositories.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    private TutorService underTest;

    @Mock
    private TutorRepository tutorRepository;

    @Mock private UsuarioService usuarioService;

    @Mock
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        underTest = new TutorService(tutorRepository, usuarioService);
    }

    @Test
    void itShouldAgregarUnTutor() {
        // given
        Tutor tutor = new Tutor();
        tutor.setIdUsuario(1L);
        tutor.setUsername("lucasAm");
        tutor.setEmail("luck@mail.com");
        tutor.setPassword("12345678");
        tutor.setRole(Role.ROLE_TUTOR);
        tutor.setEspecialidad(Especialidad.JAVA);
        tutor.setDisponible(false);

        // when
        Tutor tutor1 = underTest.agregarTutor(tutor);

        // then
        assertThat(tutor).isEqualTo(tutor1);
    }

    @Test
    void itShouldObtenerTodosPorEspecialidad() {
        // given
        Tutor tutor = new Tutor();
        tutor.setIdUsuario(1L);
        tutor.setUsername("lucasAm");
        tutor.setEmail("luck@mail.com");
        tutor.setPassword("12345678");
        tutor.setRole(Role.ROLE_TUTOR);
        tutor.setFechaCreacion(LocalDateTime.now());
        tutor.setEspecialidad(Especialidad.JAVA);
        tutor.setDisponible(false);
        given(tutorRepository.findAllByEspecialidad(any())).willReturn(List.of(tutor));
        given(tutorRepository.existsByEspecialidad(Especialidad.JAVA)).willReturn(true);


        // when
        List<Tutor> tutors = underTest.obtenerTodoPorEspecialidad(Especialidad.JAVA);
        int actual = tutors.size();
        int expected = 1;

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void itShouldThrowExceptionWhenEspecialidadDoesNotExists() {
        // given
        given(tutorRepository.existsByEspecialidad(any())).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> underTest.obtenerTodoPorEspecialidad(Especialidad.CSHARP))
                .isInstanceOf(EdhubExceptions.class)
                .hasMessageContaining("Especialidad no hallada", HttpStatus.NOT_FOUND);
    }
}