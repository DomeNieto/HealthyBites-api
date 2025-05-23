package com.healthybites.configuration;

import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
					title = "HealthyBites API",
					description = "REST API for managing users and health data in the HealthyBites app.",
					termsOfService = "https://healthybites.com/terminos",
					version = "1.0.0",
					contact = @Contact(
				            name = "Doménica Nieto y Claudia Rivas",
				            url = "https://healthybites.com",
				            email = "contacto@healthybites.com"
				    ),
					license = @License(
							name="Standard software use license for -acceso datos-",
							url = "https://healthybites.com/licencia"
					)
					
				),
		servers = {
				@Server(
						description = "Server URL in producion environment",
						url="http://localhost:8081"
						)
		},
		security = @SecurityRequirement(
				name = "Security Basic"
				)
		)

@SecurityScheme(
		name = "Security Basic",
		description = "User/Password for JPA REPOSITORY",
		type = SecuritySchemeType.HTTP,
		paramName = HttpHeaders.AUTHORIZATION,
		in = SecuritySchemeIn.HEADER,
		scheme = "basic"
)
public class OpenAPIConfig {

}