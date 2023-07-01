package com.edhub.services;

import com.paypal.api.payments.Payment;
import org.springframework.stereotype.Service;

import com.edhub.models.PagoDetalle;
import com.edhub.repositories.PagoDetalleRepository;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
//clase de servicio para entidad pago detalle
public class PagoDetalleService {

    private final PagoDetalleRepository pagoDetalleRepository;

    // recibe el objeto Payment(generado después que se completa con éxito un pago) y extrae datos para generar el detalle
    public void agregarPagoDetalle(Payment payment) {
        PagoDetalle pago = new PagoDetalle();
        pago.setNombrePersona(payment.getPayer().getPayerInfo().getFirstName());
        pago.setMetodoPago(payment.getPayer().getPaymentMethod());
        pago.setTotal(Double.valueOf(payment.getTransactions().get(0).getAmount().getTotal()));
        pago.setMoneda(payment.getTransactions().get(0).getAmount().getCurrency());
        pago.setFecha(LocalDateTime.parse(payment.getCreateTime()));
        pagoDetalleRepository.save(pago);
    }
    
}
