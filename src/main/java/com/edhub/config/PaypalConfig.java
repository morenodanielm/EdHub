package com.edhub.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
// contendrá toda la configuración para integración de PayPal
public class PaypalConfig {

	// los valores inyectados desde el application properties nos permitirán integrar la API PayPal
	@Value("${paypal.client.id}")
	private String clientId;
	@Value("${paypal.client.secret}")
	private String clientSecret;
	@Value("${paypal.mode}")
	private String mode;

	// configuración del sdk, en este caso el modo el cuál es sandbox
	@Bean
	public Map<String, String> paypalSdkConfig() {
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		return configMap;
	}

	// configuración del contexto de la API para poder llamarla
	@Bean
	public APIContext apiContext() throws PayPalRESTException {
		APIContext context = new APIContext(clientId, clientSecret, paypalSdkConfig().get("mode"));
		context.setConfigurationMap(paypalSdkConfig());
		return context;
	}
}
