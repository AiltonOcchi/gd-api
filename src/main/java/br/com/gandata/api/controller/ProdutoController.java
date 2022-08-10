package br.com.gandata.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gandata.api.dto.ProdutoDto;
import br.com.gandata.api.model.ProdutoModel;
import br.com.gandata.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Gerenciamento dos produtos")
@SecurityRequirement(name = "Authorization")// Exige na Open API authenticação de todos os endpoints desta classe
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	/**
	 * Lista todos os produtos
	 */
	@GetMapping("")
	@Operation(summary = "Lista todas os produtos cadastrados")
	public ResponseEntity<List<ProdutoDto>> listarTodos(){
		List<ProdutoDto> produtos = produtoService.listarTodos();
		produtos.forEach(p -> {
			p.add(linkTo(methodOn(ProdutoController.class).buscarPorId(p.getId())).withSelfRel());
			p.getCategoria().add(linkTo(methodOn(CategoriaController.class).buscarPorId(p.getCategoria().getId())).withSelfRel());
		});
		return ResponseEntity.ok(produtos);
	}
	
	
	/**
	 * Lista todos os produtos com paginação de registros
	 */
	@GetMapping("/listarPaginado")
	@Operation(summary = "Lista todas os produtos cadastrados com paginação de registros")
	public ResponseEntity<Page<ProdutoDto>> listarPaginado(
			@PageableDefault(page = 0, size = 2, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok(produtoService.listarPaginado(pageable));
	}
	
	
	/**
	 * Insere um novo produto caso ele não exista
	 */
	@PostMapping("")
	@Operation(summary = "Cadastra um novo produto (com validação)")
	public ResponseEntity<ProdutoModel> inserir(@RequestBody @Valid ProdutoModel produtoModel){
		Optional<ProdutoModel> produto = produtoService.buscaPorNome(produtoModel.getNome());
		if(produto.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(produtoService.adicionar(produtoModel));
	}
	
	
	/**
	 * Busca um produto pelo id
	 */
	@GetMapping("/{idProduto}")
	@Operation(summary = "Busca um produto a partir do ID")
	public ResponseEntity<ProdutoDto> buscarPorId(@PathVariable Long idProduto){
		return produtoService.buscarPorId(idProduto)
				.map(p -> { 
					p.add(linkTo(methodOn(ProdutoController.class).listarTodos()).withRel("Lista de Produtos"));
					p.getCategoria().add(linkTo(methodOn(CategoriaController.class).buscarPorId(p.getCategoria().getId())).withSelfRel());
					return ResponseEntity.ok(p);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	
	/**
	 * Altera os dados de um produto
	 */
	@PutMapping("/{idProduto}")
	@Operation(summary = "Atualiza um produto")
	public ResponseEntity<ProdutoDto> atualizar(@PathVariable Long idProduto, @RequestBody ProdutoModel produtoModel){
		return produtoService.atualizar(idProduto, produtoModel)
				.map(c -> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build());
		
	}
	
	
	/*
	 * Deleta um produto
	 */
	@DeleteMapping("/{idProduto}")
	@Operation(summary = "Deleta um produto")
	public ResponseEntity<?> deletarProduto(@PathVariable Long idProduto){
		return produtoService.deletar(idProduto) ? 
				ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}


