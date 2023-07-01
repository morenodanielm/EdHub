package com.edhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
// se habilita el message broker(Intermediario de mensaje o agente de mensajes)
@EnableWebSocketMessageBroker
// configura el protocolo de comunicación websocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Configure las opciones del agente de mensajes o intermediario de mensajes.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	config.enableSimpleBroker("SECURED_CHAT_SPECIFIC_USER");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/secured/user");
    }

    // Registra los endpoints STOMP asignando cada uno a una URL específica y (opcionalmente) habilitar y
    // configurar las opciones de reserva de SockJS
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	registry.addEndpoint("SECURED_CHAT_ROOM").withSockJS();
        registry.addEndpoint("SECURED_CHAT").withSockJS();
    }
}
