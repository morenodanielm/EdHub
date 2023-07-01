package com.edhub.models;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "calificaciones")
// clase modelo que representa la tabla calificaciones en la BD
public class Calificacion {

    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private Long idCalificacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calificado_id", referencedColumnName = "id_usuario")
    @NotNull
    private Usuario calificado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calificador_id", referencedColumnName = "id_usuario")
    @NotNull
    private Usuario calificador;

    @Column
    private int calificacion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    // constructores
    public Calificacion() {
    }

    public Calificacion(@NotNull Usuario calificado, @NotNull Usuario calificador, int calificacion, LocalDateTime fechaCreacion) {
        this.calificado = calificado;
        this.calificador = calificador;
        this.calificacion = calificacion;
        this.fechaCreacion = fechaCreacion;
    }

    // getter y setter
    public Long getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Long idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Usuario getCalificado() {
        return calificado;
    }

    public void setCalificado(Usuario calificado) {
        this.calificado = calificado;
    }

    public Usuario getCalificador() {
        return calificador;
    }

    public void setCalificador(Usuario calificador) {
        this.calificador = calificador;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
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
        Calificacion that = (Calificacion) o;
        return calificacion == that.calificacion && Objects.equals(idCalificacion, that.idCalificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCalificacion);
    }

    // proporciona información del objeto actual
    @Override
    public String toString() {
        return "Calificacion{" +
                "idCalificacion=" + idCalificacion +
                ", calificacion=" + calificacion +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }

    // este método se ejecutará antes de persistir el objeto mensaje para agregar la fecha actual
    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDateTime.now();
    }
}
