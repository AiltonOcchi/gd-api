package br.com.gandata.api.config;

import javax.servlet.http.HttpServletRequest;

import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.gandata.api.validation.ErroDeFormularioDto;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Value("${info.app.description}")
	private String appDesciption;

	@Value("${info.app.version}")
	private String appVersion;

	@Bean
	public OpenAPI customOpenAPI() {

		SpringDocUtils.getConfig().addResponseTypeToIgnore(Pageable.class);
		SpringDocUtils.getConfig().addResponseTypeToIgnore(Sort.class);
		SpringDocUtils.getConfig().addResponseTypeToIgnore(ErroDeFormularioDto.class);
		SpringDocUtils.getConfig().removeRequestWrapperToIgnore(HttpServletRequest.class);

		return new OpenAPI()
				.components(new Components().addSecuritySchemes("Authorization", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.info(new Info().title("Glan Data API").version(appVersion).description(appDesciption)
				//.termsOfService("http://swagger.io/terms/")
				//.license(new License(). name("Apache 2.0"). url("http://springdoc.org"))
				);
	}

	
	// Permite adicionar menu de navegação entre as URIs
	/*
	@Bean
	public GroupedOpenApi clienteApi() {
		return GroupedOpenApi.builder().group("Clientes").pathsToMatch("/clientes/**").build();
	}
	
	@Bean
	public GroupedOpenApi produtoApi() {
		return GroupedOpenApi.builder().group("Produtos").pathsToMatch("/produtos/**").build();
	}

	@Bean
	public GroupedOpenApi categoriaApi() {
		return GroupedOpenApi.builder().group("Categorias").pathsToMatch("/categorias/**").build();
	}
	*/
}