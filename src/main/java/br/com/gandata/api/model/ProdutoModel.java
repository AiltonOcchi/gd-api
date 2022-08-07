package br.com.gandata.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
		name = "produtos",
		indexes = {@Index(name="id_produto", columnList = "id")},
		uniqueConstraints = {@UniqueConstraint(name = "produto_nome_unique", columnNames = "nome")}
		)
@Schema( name = "Produto")
public class ProdutoModel {
	
	public ProdutoModel() {
	}
	
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter @Setter
	@NotBlank(message = "Informe o nome do produto")
	private String nome;
	
	@Getter @Setter
	private String descricao;
	
	@Getter @Setter
	@NotNull(message = "Informe o preço do produto")
	@NumberFormat(pattern = "#,###,###,###.##")
	@Column(name="preco", precision = 11, scale = 2, columnDefinition = "Decimal(11,2)")
	private BigDecimal preco;
	
	@Getter @Setter
	@NotNull(message = "Selecione uma categoria")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_categoria"))
	private CategoriaModel categoria;
	
	@Getter @Setter
	private LocalDate dataCadastro = LocalDate.now();
	
	// Mapper para sobrescrita de valores
	public void mapper(ProdutoModel produto) {
		if(produto.getId()!=null) this.id = produto.getId();
		if(produto.getNome()!=null) this.nome = produto.getNome();
		if(produto.getDescricao()!=null) this.descricao = produto.getDescricao();
		if(produto.getPreco()!=null) this.preco = produto.getPreco();
		if(produto.getCategoria()!=null) this.categoria = produto.getCategoria();
	}
	
}
