package com.edhub.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tutores")
public class Tutor extends Usuario{

    private Especialidad especialidad;

    private boolean disponible;

    public Tutor() {

    }

    public Tutor(Especialidad especialidad, boolean disponible) {
        super();
        this.especialidad = especialidad;
        this.disponible = disponible;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "especialidad=" + especialidad +
                ", disponible=" + disponible +
                '}';
    }
}
