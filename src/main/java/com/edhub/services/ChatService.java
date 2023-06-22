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
public class ChatService {

    private final ChatRepository chatRepository;

    public void agregarChat(Chat chat) {
        if(chatRepository.existsById(chat.getId())) {
            throw new EdhubExceptions("El chat ya existe", HttpStatus.CONFLICT);
        }
        chatRepository.save(chat);
    }

    public List<Chat> obtenerTodos() {
        return chatRepository.findAll();
    }

    public Chat obtenerPorId(Long id) {
        if(!chatRepository.existsById(id)) {
            throw new EdhubExceptions("El chat no existe", HttpStatus.NOT_FOUND);
        }
        return chatRepository.findById(id).get();
    }

    public void eliminarChat(Long id) {
        if(!chatRepository.existsById(id)) {
            throw new EdhubExceptions("El chat no existe", HttpStatus.NOT_FOUND);
        }
        chatRepository.deleteById(id);
    }
    
}
