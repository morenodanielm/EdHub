package com.edhub.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
  info =@Info(
    title = "Edhub API",
    version = "3.0.1",
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
    url = "localhost:8080",
    description = "Production"
  )
)
public class OpenApiConfig {
    
    /*@Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Edhub")
                .pathsToExclude("/api/v1/auth/**")
                .build();
    }*/

    @Bean
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
