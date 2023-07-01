package com.edhub.repositories;

import com.edhub.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
// clase test para usuario repository
public class UsuarioRepositoryTest {

    @Autowired
    // objeto bajo test
    private UsuarioRepository underTest;

    @Mock
    private Usuario usuario;

    @BeforeEach
    // se ejecutará antes de cada método test para instanciar el objeto usuario
    void setUp() {
        usuario = new Usuario(
                "carlos19",
                "carlos@mail.com",
                "12345678",
                Role.ROLE_USER,
                LocalDateTime.now());
    }

    @Test
    // testea que efectivamente se pueda eliminar un usuario
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
    // verifica que efectivamente se pueda evalular la existencia de un usuario por su id
    void itShouldVerifyIfUserExistsById() {
        // given
        underTest.save(usuario);

        // when
        boolean exists = underTest.existsById(usuario.getIdUsuario());

        // then 
        assertThat(exists).isTrue();
    }

    @Test
    // evalúaque se pueda obtener un usuario por su username
    void itShouldFindByUsername() {
        // given
        underTest.save(usuario);

        // when
        Optional<Usuario> usuarioOptional = underTest.findByUsername("carlos19");
        Usuario u = usuarioOptional.orElse(null);

        // then
        assertThat(u).isEqualTo(usuario);
    }
}
