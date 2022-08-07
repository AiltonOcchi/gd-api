package br.com.gandata.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "Produto")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDto {
	
	@Getter @Setter
	private Long id;

	@Getter @Setter
	private String nome;

	@Getter @Setter
	private String descricao;

	@Getter @Setter
	private BigDecimal preco;

	@Getter @Setter
	private CategoriaDto categoria;

	@Getter @Setter
	private LocalDate dataCadastro;
}
