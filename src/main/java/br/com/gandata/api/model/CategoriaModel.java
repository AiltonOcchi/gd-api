package br.com.gandata.api.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
		name = "categorias",
		indexes = {@Index(name="id_categoria", columnList = "id")},
		uniqueConstraints = {@UniqueConstraint(name = "categoria_nome_unique", columnNames = "nome")}
		)
@Schema( name = "Categoria")
public class CategoriaModel {
	
	public CategoriaModel() {}
	
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter @Setter
	@NotBlank
	@Size(max = 20)
	private String nome;
	
	@Getter @Setter
	private String descricao;
	
	@Getter @Setter
	@OneToMany(mappedBy = "categoria")
	private List<ProdutoModel> produtos;

}
