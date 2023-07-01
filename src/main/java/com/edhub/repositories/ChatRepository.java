package com.edhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edhub.models.Chat;

// clase de acceso a datos para la entidad chat
public interface ChatRepository extends JpaRepository<Chat, Long>{
    
}
