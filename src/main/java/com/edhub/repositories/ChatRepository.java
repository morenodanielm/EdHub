package com.edhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edhub.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long>{
    
}
