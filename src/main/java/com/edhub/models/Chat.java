package com.edhub.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "chats")
// clase modelo que representa la tabla chats en la BD
public class Chat {

    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes;

    // constructor
    public Chat() {
        this.mensajes = new ArrayList<>();
    }

    // getter y setter
    public Long getId() {
        return id;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void addMensaje(Mensaje mensaje) {
        if(this.mensajes != null) {
            this.mensajes.add(mensaje);
        }
    }

    // métodos para comparar objetos de esta clase
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // proporciona información del objeto actual
    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", mensajes=" + mensajes +
                '}';
    }

}
