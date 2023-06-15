package com.edhub.services;

import com.edhub.models.Role;
import com.edhub.models.Tutor;
import com.edhub.models.Usuario;
import com.edhub.repositories.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    private TutorService underTest;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        underTest = new TutorService(tutorRepository);
    }

    @Test
    void itShouldObtenerTodosPorEspecialidad() {
        // given
        // todo: search as create user-tutor from an object user
//        given(tutorRepository.findAllByEspecialidad(any())).willReturn(List.of(
//                        Usuario.builder()
//                                .idUsuario(1L)
//                                .username("Antonio Lucas")
//                                .email("anton@mail.com")
//                                .role(Role.ROLE_TUTOR)
//                                .fechaCreacion(LocalDateTime.now())
//                                .password("12345678")
//                                .build(),
//                        Tutor.builder()
//                                .idUsuario(2L)
//                                .username("Mariano")
//                                .email("mariano@mail.com")
//                                .role(Role.ROLE_TUTOR)
//                                .fechaCreacion(LocalDateTime.now())
//                                .password("12345678")
//                                .build(),
//                        Tutor.builder()
//                                .idUsuario(3L)
//                                .username("Fernanda")
//                                .email("fer@mail.com")
//                                .role(Role.ROLE_TUTOR)
//                                .fechaCreacion(LocalDateTime.now())
//                                .password("12345678")
//                                .build()));
//
//        // when
//        underTest.obtenerTodoPorEspecialidad()
        // then
    }
}