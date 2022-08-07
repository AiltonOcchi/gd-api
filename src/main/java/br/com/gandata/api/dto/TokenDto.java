package br.com.gandata.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "Token")
public class TokenDto {

	public TokenDto(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	@Getter
	private String token;
	
	@Getter
	private String tipo;
	
}
