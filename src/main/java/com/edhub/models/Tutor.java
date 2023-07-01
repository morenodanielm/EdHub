package com.edhub.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "tutores")
// clase modelo que representa la tabla tutores en la BD
public class Tutor extends Usuario{

    // atributos
    @Column(name = "especialidad")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private EnumSet<Especialidad> especialidades;

    @Column(name = "disponible")
    private boolean disponible;

    // constructores
    public Tutor() {

    }

    public Tutor(boolean disponible) {
        super();
        this.disponible = disponible;
    }

    // getter y setter
    public EnumSet<Especialidad> getEspecialidad() {
        return especialidades;
    }

    public void setEspecialidad(EnumSet<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // métodos para comparar objetos de esta clase
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // proporciona información del objeto actual
    @Override
    public String toString() {
        return "Tutor{" +
                "especialidad=" + especialidades +
                ", disponible=" + disponible +
                '}';
    }
}
