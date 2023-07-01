package com.edhub.models;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "comentarios")
// clase modelo que representa la tabla comentarios en la BD
public class Comentario {

    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calificacion_id", referencedColumnName = "id_calificacion")
    @NotNull
    private Calificacion calificacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    @NotNull
    private Usuario usuario;

    @Column(name = "comentario")
    @NotBlank
    private String comentario;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;

    // constructores
    public Comentario() {
    }

    public Comentario(@NotNull Calificacion calificacion, @NotNull Usuario usuario, @NotBlank String comentario, LocalDateTime fechaCreacion) {
        this.calificacion = calificacion;
        this.usuario = usuario;
        this.comentario = comentario;
        this.fechaCreacion = fechaCreacion;
    }

    // getter y setter
    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // métodos para comparar objetos de esta clase
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comentario that = (Comentario) o;
        return Objects.equals(idComentario, that.idComentario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idComentario);
    }

    // proporciona información del objeto actual
    @Override
    public String toString() {
        return "Comentario{" +
                "idComentario=" + idComentario +
                ", calificacion=" + calificacion +
                ", comentario='" + comentario + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }

    // este método se ejecutará antes de persistir el objeto mensaje para agregar la fecha actual
    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDateTime.now();
    }
}
