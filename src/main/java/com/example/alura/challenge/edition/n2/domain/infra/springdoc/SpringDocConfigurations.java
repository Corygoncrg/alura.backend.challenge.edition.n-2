package com.example.alura.challenge.edition.n2.domain.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Example")
                        .description("API Rest example, having CRUD methods as well as custom queries for monthly Summary with authentication")
                        .contact(new Contact()
                                .name("Rodrigo H. De Oliveira")
                                .email("rodrigohajer11@hotmail.com"))
                        .license(new License()
                                .name("Github")
                                .url("https://github.com/Corygoncrg/alura.backend.challenge.edition.n-2.git")));
    }


}
