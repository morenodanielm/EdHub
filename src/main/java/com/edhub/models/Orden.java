package com.edhub.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
// clase que representará una orden de pago para PayPal
public class Orden {

	// atributos
	private Double precio;

	private String moneda;

	private String metodo;

	private String intencion;

	private String descripcion;
}
