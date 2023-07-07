package com.edhub.models;

import java.util.EnumSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

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

    @Column(name = "sobre_mi")
    private String sobreMi;

    // constructores
    public Tutor() {

    }

    public Tutor(boolean disponible, String sobreMi) {
        super();
        this.disponible = disponible;
        this.sobreMi = sobreMi;
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

    public EnumSet<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(EnumSet<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public String getSobreMi() {
        return sobreMi;
    }

    public void setSobreMi(String sobreMi) {
        this.sobreMi = sobreMi;
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
