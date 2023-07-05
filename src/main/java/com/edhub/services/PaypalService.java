package com.edhub.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.edhub.repositories.PagoDetalleRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
//clase de servicio para PayPal
public class PaypalService {
	
	private final APIContext apiContext;
	private final PagoDetalleRepository ordenRepository;
	
	
	// generará el objeto Payment con lo relacionado al pago(monto, método, descrición, etc)
	public Payment createPayment(
			Double total, 
			String moneda, 
			String metodo,
			String intencion,
			String descripción, 
			String cancelUrl, 
			String successUrl) throws PayPalRESTException{
		Amount amount = new Amount();
		amount.setCurrency(moneda);
		BigDecimal big = new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
		total = big.doubleValue();
		amount.setTotal(String.valueOf(total));

		Transaction transaction = new Transaction();
		transaction.setDescription(descripción);
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(metodo.toString());

		Payment payment = new Payment();
		payment.setIntent(intencion.toString());
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

		return payment.create(apiContext);
	}
	
	// ejecutará el pago
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
}
