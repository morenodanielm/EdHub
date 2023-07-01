package com.edhub.services;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.*;
import com.edhub.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    private UsuarioService underTest;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        underTest = new UsuarioService(usuarioRepository);
        usuario = Usuario.builder()
                .username("daniel_14")
                .email("daniel@mail.com")
                .password("12345678")
                .role(Role.ROLE_USER)
                .fechaCreacion(LocalDateTime.now())
                .build();
    }

    @Test
    void itShouldAddUser() {
        // given

        // when
        underTest.agregarUsuario(usuario);

        // then
        // usado para capturar el argumento cuando se llama el m�todo save del repository
        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        // obtiene el argumento capturado
        Usuario actual = usuarioArgumentCaptor.getValue();
        // compara el actual con el esperado
        assertThat(actual).isEqualTo(usuario);
    }

    @Test
    void itShouldGetByUsername() {
        // given
        // cuando se invoque el m�todo findByUsername con cualquier string como par�metro, retornaremos un usuario, esto para evitar exception
        given(usuarioRepository.findByUsername(anyString()))
                .willReturn(Optional.of(
                        Usuario.builder()
                                .username("faustog1")
                                .build()));
        // when
        String username = "faustog1";
        underTest.obtenerPorUsername(username);

        // then
        verify(usuarioRepository).findByUsername(anyString());
    }

    @Test
    void itShouldDeleteUserByUsername() {
        // given
        given(usuarioRepository.existsById(anyLong()))
                .willReturn(true);

        // when
        underTest.eliminarUsuario(1L);

        // then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(usuarioRepository).deleteById(argumentCaptor.capture());
        Long actual = argumentCaptor.getValue();
        assertThat(actual).isEqualTo(1L);
    }

    @Test
    void itShouldUpdateUser() {
        // given
        given(usuarioRepository.existsById(anyLong()))
                .willReturn(true);

        // when
        String expected = "faustog1";
        usuario.setUsername(expected);
        usuario.setIdUsuario(1L);
        underTest.actualizarUsuario(usuario);

        // then
        ArgumentCaptor<Usuario> argumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(argumentCaptor.capture());
        String actual = argumentCaptor.getValue().getUsername();
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    // verificará que se pueda obtener todos los usuarios de manera correcta
    void itShouldGetAll() {
        // given
        given(usuarioRepository.findAll())
                .willReturn(List.of(
                        Usuario.builder().build(),
                        Usuario.builder().build(),
                        Usuario.builder().build()));

        // when
        List<Usuario> usuarios = underTest.obtenerTodos();
        int actual = usuarios.size();
        int expected = 3;

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    // test para verificar que se arroje la excepción cuando se debe lanzar
    void itShouldThrowExceptionWhenUsernameExists() {
        // given
        given(usuarioRepository.existsByUsername(anyString()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.agregarUsuario(this.usuario))
                .isInstanceOf(EdhubExceptions.class)
                .hasMessageContaining("El usuario ya existe", HttpStatus.CONFLICT);
    }

    @Test
    // test para verificar que se arroje la excepción cuando se debe lanzar
    void itShouldThrowExceptionWhenUsernameDoesNotExists() {
        // given
        String username = "juan23";

        // when
        // then
        assertThatThrownBy(() -> underTest.obtenerPorUsername(username))
                .isInstanceOf(EdhubExceptions.class)
                .hasMessageContaining("Usuario " + username + " no hallado", HttpStatus.NOT_FOUND);
    }
}