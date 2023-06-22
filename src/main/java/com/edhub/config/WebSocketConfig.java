package com.edhub.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import java.util.List;

@Configuration
// se habilita el message broker(Intermediario de mensaje o agente de mensajes)
@EnableWebSocketMessageBroker
/* implementamos la interface WebSocketMessageBrokerConfigurer define métodos para
configurar el control de mensajes con protocolos de mensajería simple.  (por ejemplo, STOMP) de clientes WebSocket.
Stomp (Streaming Text Orientated Messaging Protocol) es un protocolo textual, sencillo y simple de implementar que
permite comunicar clientes STOMP con cualquier Message Broker STOMP
Normalmente se utiliza para personalizar la configuración proporcionada a través de @EnableWebSocketMessageBroker.
*/
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Configure las opciones del agente de mensajes o intermediario de mensajes.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /*
        si llamamos a enableSimpleBroker("/user"), estamos configurando un broker de mensajes simple con el prefijo de destino
        "/user". Esto significa que los clientes pueden suscribirse a cualquier destino que comience con "/user" para recibir
         los mensajes enviados a ese destino.
        */
        config.enableSimpleBroker( "/user");
        /*
        setApplicationDestinationPrefixes("/app") establece el prefijo de destino de la aplicación en "/app".
        Esto significa que cuando los clientes envíen mensajes al servidor, deben usar patrones de destino que comiencen con "/app".
        Por ejemplo, si un cliente desea enviar un mensaje al endpoint "/hello" del servidor, enviaría el mensaje a "/app/hello".
         */
        config.setApplicationDestinationPrefixes("/app");
        /*
        setUserDestinationPrefix configura el prefijo utilizado para identificar las destinos de usuario, por ejemplo, cuando un
        usuario intenta suscribirse a "/user/queue/position-updates", la destinación puede traducirse a "/queue/position-updatesi9oqdfzo",
        lo que genera un nombre de cola único que no entra en conflicto con ningún otro usuario que intente hacer lo mismo.
        */
        config.setUserDestinationPrefix("/user");
    }

    // Registra los endpoints STOMP asignando cada uno a una URL específica y (opcionalmente) habilitar y
    // configurar las opciones de reserva de SockJS
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                /*Establece los orígenes desde los cuales se permiten las solicitudes de origen cruzado desde un navegador
                  En resumen, permitirá las solicitudes de apps externas*/
                .setAllowedOrigins("*")
                // SockJS es una biblioteca de JavaScript para navegadores que proporciona un objeto similar a WebSocket.
                .withSockJS();
    }


    /* 
     Configura los convertidores de mensajes que se utilizarán al extraer el contenido de los mensajes en los métodos anotados y al enviar mensajes 
     (por ejemplo, a través del "broker" SimpMessagingTemplate).
     La lista proporcionada, inicialmente vacía, se puede utilizar para agregar convertidores de mensajes, mientras que el valor booleano de retorno
     se utiliza para determinar si también se deben agregar los convertidores de mensajes por defecto.
     MessageConverter es un convertidor para transformar el contenido del payload de un mensaje de su forma serializada a un objeto tipado y viceversa.
    */
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        /* 
         Establece el tipo MIME(el tipo Extensiones multipropósito de Correo de Internet (MIME) es una forma estandarizada de indicar la naturaleza
         y el formato de un documento, archivo o conjunto de datos) predeterminado a utilizar cuando no hay un encabezado MessageHeaders.CONTENT_TYPE
         presente.
        */
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        //Establece el ObjectMapper para este convertidor. Si no se establece, se utilizará un ObjectMapper predeterminado.
        converter.setObjectMapper(new ObjectMapper());
        //Configura el ContentTypeResolver que se utilizará para resolver el tipo de contenido de un mensaje de entrada.
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }
}
