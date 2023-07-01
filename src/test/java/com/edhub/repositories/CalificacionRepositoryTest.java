package com.edhub.repositories;

import java.time.LocalDateTime;
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

@DataJpaTest
@ExtendWith(MockitoExtension.class)
// clase test para calificacion repository
public class CalificacionRepositoryTest {

    @Autowired
    // objeto bajo test
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
    // se ejecutará antes de cada método test para instanciar tres objetos
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

    // test para verficar que se puede obtener una calificación con el usuario calificado
    @Test
    void itShouldFindByUsuarioCalificado() {
        // given

        // establecer datos para la entidad usuario
        usuarioCalificado.addCalificacionesCalificado(calificacion);
        usuarioCalificador.addCalificacionesCalificador(calificacion);

        // guardar usuarios
        usuarioRepository.save(usuarioCalificado);
        usuarioRepository.save(usuarioCalificador);

        // establecer calificadores
        calificacion.setCalificado(usuarioCalificado);
        calificacion.setCalificador(usuarioCalificador);

        // guardar calificacion
        System.out.println(underTest.save(calificacion));

        // when
        Optional<Calificacion> calificacionOptional = underTest.findByCalificado(usuarioCalificado);
        Calificacion actual = calificacionOptional.orElse(null);

        // then
        assertThat(actual).isEqualTo(calificacion);
    }

    // test para verficar que se puede obtener una calificación con el usuario calificador
    @Test
    void itShouldFindByUsuarioCalificador() {
        // given

        // establecer datos para la entidad usuario
        usuarioCalificado.addCalificacionesCalificado(calificacion);
        usuarioCalificador.addCalificacionesCalificador(calificacion);

        // guardar usuarios
        usuarioRepository.save(usuarioCalificado);
        usuarioRepository.save(usuarioCalificador);

        // establecer calificadores
        calificacion.setCalificado(usuarioCalificado);
        calificacion.setCalificador(usuarioCalificador);

        // guardar calificacion
        underTest.save(calificacion);

        // when
        Optional<Calificacion> calificacionOptional = underTest.findByCalificador(usuarioCalificador);
        Calificacion actual = calificacionOptional.orElse(null);

        // then
        assertThat(actual).isEqualTo(calificacion);
    }
}
