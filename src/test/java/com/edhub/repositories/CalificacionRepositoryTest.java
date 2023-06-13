package com.edhub.repositories;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import com.edhub.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.edhub.models.Calificacion;
import com.edhub.models.Usuario;
import com.edhub.utils.GetUsuario;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class CalificacionRepositoryTest {

    @Autowired
    private CalificacionRepository underTest;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // usuario calificador
    @Mock
    private Usuario usuarioCalificador;

    // usuario calificado
    @Mock
    private Usuario usuarioCalificado;

    @Mock
    private Calificacion calificacion;

    @BeforeEach
    void setUp() {
        usuarioCalificador = new Usuario(
                "carlos19",
                "carlos@mail.com",
                "12345678",
                Role.ROLE_USER,
                LocalDateTime.now());

        // usuario 2
        usuarioCalificado = new Usuario(
                "daniel21",
                "daniel@mail.com",
                "12345678",
                Role.ROLE_ADMIN,
                LocalDateTime.now());

        calificacion = Calificacion.builder()
                .fechaCreacion(LocalDateTime.now())
                .calificacion(5)
                .calificado(usuarioCalificado)
                .calificador(usuarioCalificador)
                .build();
    }

    @Test
    void itShouldFindByUsuarioCalificado() {
        // given

        // set data for entity usuario
        usuarioCalificado.addCalificacionesCalificado(calificacion);
        usuarioCalificador.addCalificacionesCalificador(calificacion);

        // save usuarios
        usuarioRepository.save(usuarioCalificado);
        usuarioRepository.save(usuarioCalificador);

        // set calificadores
        calificacion.setCalificado(usuarioCalificado);
        calificacion.setCalificador(usuarioCalificador);

        // save calificacion
        System.out.println(underTest.save(calificacion));

        // when
        Optional<Calificacion> calificacionOptional = underTest.findByCalificado(usuarioCalificado);
        Calificacion actual = calificacionOptional.orElse(null);

        // then
        assertThat(actual).isEqualTo(calificacion);
    }

    @Test
    void itShouldFindByUsuarioCalificador() {
        // given

        // set data for entity usuario
        usuarioCalificado.addCalificacionesCalificado(calificacion);
        usuarioCalificador.addCalificacionesCalificador(calificacion);

        // save usuarios
        usuarioRepository.save(usuarioCalificado);
        usuarioRepository.save(usuarioCalificador);

        // set calificadores
        calificacion.setCalificado(usuarioCalificado);
        calificacion.setCalificador(usuarioCalificador);

        // save calificacion
        System.out.println(underTest.save(calificacion));

        // when
        Optional<Calificacion> calificacionOptional = underTest.findByCalificador(usuarioCalificador);
        Calificacion actual = calificacionOptional.orElse(null);

        // then
        assertThat(actual).isEqualTo(calificacion);
    }
}
