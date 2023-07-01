package com.edhub.controllers;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edhub.services.MensajeService;
import com.edhub.services.dto.MensajeDTO;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
@Controller
// contendrá recibirá todos los mensajes enviados al chat
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MensajeService mensajeService;

    // recibirá los mensajes y los enviará al intermediario de mensajes
    @MessageMapping("/chat.sendMessage")
    public void procesarMensaje(@Payload MensajeDTO mensajeDTO) {
        mensajeService.agregarMensaje(mensajeDTO);
        messagingTemplate.convertAndSendToUser(mensajeDTO.getDestinatario(), "/user", mensajeDTO.getContenido());
    }
}
