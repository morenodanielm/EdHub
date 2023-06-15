package com.edhub.services;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.Mensaje;
import com.edhub.models.Usuario;
import com.edhub.repositories.MensajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MensajeServiceTest {

    private MensajeService underTest;

    @Mock
    private MensajeRepository mensajeRepository;

    @BeforeEach
    void setUp() {
        underTest = new MensajeService(mensajeRepository);
    }

    @Test
    void itShouldAgregarMensaje() {
        // given
        Mensaje mensaje = Mensaje.builder()
                .idMensaje(1L)
                .mensaje("Hola, qué tal?")
                .fechaCreacion(LocalDateTime.now())
                .usuario(Usuario.builder().build())
                .build();

        // when
        underTest.agregarMensaje(mensaje);

        // then
        ArgumentCaptor<Mensaje> argumentCaptor = ArgumentCaptor.forClass(Mensaje.class);
        verify(mensajeRepository).save(argumentCaptor.capture());
        Mensaje actual = argumentCaptor.getValue();
        assertThat(actual).isEqualTo(mensaje);
    }

    @Test
    void itShouldObtenerTodos() {
        // given
        given(mensajeRepository.findAll()).willReturn(List.of(Mensaje.builder().build()));

        // when
        List<Mensaje> mensajes = underTest.obtenerTodos();
        int actual = mensajes.size();
        int expected = 1;

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void itShouldEliminarMensaje() {
        // given
        Mensaje mensaje = Mensaje.builder()
                .idMensaje(1L)
                .mensaje("Hola, qué tal?")
                .fechaCreacion(LocalDateTime.now())
                .usuario(Usuario.builder().build())
                .build();
        given(mensajeRepository.existsById(anyLong())).willReturn(true);

        // when
        underTest.eliminarMensaje(mensaje);

        // then
        ArgumentCaptor<Mensaje> argumentCaptor = ArgumentCaptor.forClass(Mensaje.class);
        verify(mensajeRepository).delete(argumentCaptor.capture());
        Mensaje actual = argumentCaptor.getValue();
        assertThat(actual).isEqualTo(mensaje);
    }

    @Test
    void itShouldThrowExceptionWhenMensajeDoesNotExits() {
        // given
        // when
        // then
        assertThatThrownBy(() -> underTest.eliminarMensaje(Mensaje.builder().build()))
                .isInstanceOf(EdhubExceptions.class)
                .hasMessageContaining("El mensaje no existe", HttpStatus.NOT_FOUND);
    }
}