package com.edhub.config;

import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;

@Configuration
// contiene toda la información de la API documentada con Open API
@OpenAPIDefinition(
  info =@Info(
    title = "Edhub API",
    version = "1.0",
    contact = @Contact(
      name = "Edhub", email = "edhub@mail.com", url = "https://www.edhub.com"
    ),
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    termsOfService = "terms-of-service",
    description = "Edhub API for manage of students and professors"
  ),
  servers = @Server(
    url = "http://localhost:8080",
    description = "Development"
  )
)
public class OpenApiConfig {

    //swagger ui = http://localhost:8080/swagger-ui.html
    @Bean
    // configuración de seguridad global para OpenAPI
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement()
            .addList(securitySchemeName))
            .components(new Components()
            .addSecuritySchemes(securitySchemeName, new SecurityScheme()
            .name(securitySchemeName)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")));
    }
}
