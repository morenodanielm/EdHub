package com.edhub.services;

import com.edhub.models.Mensaje;
import com.edhub.models.Usuario;
import com.edhub.repositories.MensajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MensajeServiceTest {

    // todo: implement all methods and test it
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
    void itShouldName() {
        // given
        // when
        // then
    }
}