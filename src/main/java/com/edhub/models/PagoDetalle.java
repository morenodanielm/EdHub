package com.edhub.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pagos_detalles")
@Entity
// clase modelo que representa la tabla pagos_detalles en la BD
public class PagoDetalle {

    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_persona")
    private String nombrePersona;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "total")
    private Double total;

    @Column(name = "moneda")
    private String moneda;
    
    @Column(name = "fecha")
    private LocalDateTime fecha;
}
