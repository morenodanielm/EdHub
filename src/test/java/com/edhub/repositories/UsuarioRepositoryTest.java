package com.edhub.repositories;

import com.edhub.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import com.edhub.models.Usuario;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository underTest;

    @Mock
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(
                "carlos19",
                "carlos@mail.com",
                "12345678",
                Role.ROLE_USER,
                LocalDateTime.now());
    }

    @Test
    void itShouldDeleteById() {
        // given
        underTest.save(usuario);

        // when
        underTest.deleteById(usuario.getIdUsuario());
 
        // then
        int expected = 0;
        int actual = underTest.findAll().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void itShouldVerifyIfUserExistsById() {
        // given
        underTest.save(usuario);

        // when
        boolean exists = underTest.existsById(usuario.getIdUsuario());

        // then 
        assertThat(exists).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "daniel14", // deberia fallar
        "carlos19" // deberia pasar
    })
    void itShouldFindByUsername(String username) {
        // given
        underTest.save(usuario);

        // when
        Optional<Usuario> usuarioOptional = underTest.findByUsername(username);
        Usuario u = usuarioOptional.orElse(null);

        // then
        assertThat(u).isEqualTo(usuario);
    }
}
