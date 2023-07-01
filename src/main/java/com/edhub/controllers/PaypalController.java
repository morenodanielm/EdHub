package com.edhub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edhub.models.Orden;
import com.edhub.services.*;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;

@RequestMapping("/api/v1/paypal")
@AllArgsConstructor
@RestController
// recibirá las peticiones para la realización de pagos
public class PaypalController {
	
	private final PaypalService paypalService;

	private final PagoDetalleService pagoDetalleService;
	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@PostMapping("/pagar")
	// recibe la orden de pago
	public ResponseEntity<String> pago(@RequestBody Orden orden) {
		try {
			Payment payment = paypalService.createPayment(orden.getPrecio(), orden.getMoneda(), orden.getMetodo(),
					orden.getIntencion(), orden.getDescripcion(), "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				// si los links del pago PayPal contiene uno llamado "approval_url", retornamos el link para el proceso de pago de PayPal
				if (link.getRel().equals("approval_url")) {
					return ResponseEntity.ok(link.getHref());
				}
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = CANCEL_URL)
	// recibirá peticiones de pagos que se cancelarán
	public ResponseEntity<String> cancelarPago() {
		return ResponseEntity.ok("Pago cancelado correctamente");
	}

	@GetMapping(value = SUCCESS_URL)
	// manejará los pagos que finalmente se realicen
	public ResponseEntity<Void> pagoExitoso(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			// si el estado del pago es "aprobado", se ejecutará
			if (payment.getState().equals("approved")) {
				pagoDetalleService.agregarPagoDetalle(payment);
				return ResponseEntity.ok().build();
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return ResponseEntity.internalServerError().build();
	}

}
