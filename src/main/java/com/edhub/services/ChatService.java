package com.edhub.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.Chat;
import com.edhub.repositories.ChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// clase de servicio para entidad chat
public class ChatService {

    private final ChatRepository chatRepository;

    // agregará un nuevo chat
    public void agregarChat(Chat chat) {
        // si existe, se lanzará una excepción
        if(chatRepository.existsById(chat.getId())) {
            throw new EdhubExceptions("El chat ya existe", HttpStatus.CONFLICT);
        }
        chatRepository.save(chat);
    }

    // obtener todos los chat
    public List<Chat> obtenerTodos() {
        return chatRepository.findAll();
    }

    // obtener un chat por id
    public Chat obtenerPorId(Long id) {
        if(!chatRepository.existsById(id)) {
            throw new EdhubExceptions("El chat no existe", HttpStatus.NOT_FOUND);
        }
        return chatRepository.findById(id)
                .orElseThrow(() -> new EdhubExceptions("Chat no hallado", HttpStatus.NOT_FOUND));
    }

    // eliminará un chat por id
    public void eliminarChat(Long id) {
        if(!chatRepository.existsById(id)) {
            throw new EdhubExceptions("El chat no existe", HttpStatus.NOT_FOUND);
        }
        chatRepository.deleteById(id);
    }
    
}
