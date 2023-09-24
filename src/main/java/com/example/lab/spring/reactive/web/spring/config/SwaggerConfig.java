package com.example.lab.spring.reactive.web.spring.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@SuppressWarnings("java:S1192")
@Configuration
@OpenAPIDefinition(
	security = @SecurityRequirement(name = HttpHeaders.AUTHORIZATION),
	servers = @Server(url = "${spring.webflux.base-path:}/")
)
@SecurityScheme(
	name = HttpHeaders.AUTHORIZATION,
	scheme = "Bearer",
	type = SecuritySchemeType.HTTP,
	in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {
}
