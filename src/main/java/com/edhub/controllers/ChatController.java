package com.edhub.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import com.edhub.services.MensajeService;
import com.edhub.services.dto.MensajeDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MensajeService mensajeService;

    @MessageMapping("/chat.sendMessage")
    public void procesarMensaje(@Payload MensajeDTO mensajeDTO) {
        mensajeService.agregarMensaje(mensajeDTO);
        messagingTemplate.convertAndSendToUser(mensajeDTO.getDestinatario(), 
            "/queue/messages", 
            mensajeDTO.getContenido());
    }
}
