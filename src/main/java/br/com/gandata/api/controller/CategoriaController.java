package br.com.gandata.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.gandata.api.dto.CategoriaDto;
import br.com.gandata.api.model.CategoriaModel;
import br.com.gandata.api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Gerenciamento das categorias dos produtos")
@SecurityRequirement(name = "Authorization")// Exige na Open API authenticação de todos os endpoints desta classe
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	/**
	 * Lista todos os categorias
	 */
	@GetMapping("")
	@Cacheable(value = "listaCategorias")
	@Operation(summary = "Lista todas as categorias cadastradas")
	public ResponseEntity<List<CategoriaDto>> listarTodos(){
		List<CategoriaDto> categorias = categoriaService.listarTodos();
		categorias.forEach(c ->	c.add(linkTo(methodOn(CategoriaController.class).buscarPorId(c.getId())).withSelfRel()));
		return ResponseEntity.ok(categorias);
	}
	
	
	/**
	 * Lista todos as categorias com paginação de registros
	 */
	@GetMapping("/listarPaginado")
	@Operation(summary = "Lista todas as categorias cadastradas com paginação de registro")
	public ResponseEntity<Page<CategoriaDto>> listarPaginado(
			@PageableDefault(page = 0, size = 2, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok(categoriaService.listarPaginado(pageable));
	}
	
	
	/**
	 * Insere uma nova categoria caso ela não exista
	 */
	@PostMapping("")
	@CacheEvict(value = "listaCategorias")
	@Operation(summary = "Cadastra uma nova categoria (com validação)")
	public ResponseEntity<CategoriaModel> inserir(@RequestBody @Valid CategoriaModel categoriaModel){
		Optional<CategoriaModel> categoria = categoriaService.buscaPorNome(categoriaModel.getNome());
		if(categoria.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(categoriaService.adicionar(categoriaModel));
	}
	
	
	/**
	 * Busca uma categoria pelo id
	 */
	@GetMapping("/{idCategoria}")
	@Operation(summary = "Busca uma categoria a partir do ID")
	public ResponseEntity<CategoriaDto> buscarPorId(@PathVariable Long idCategoria){
		return categoriaService.buscarPorId(idCategoria)
				.map(c -> {
					c.add(linkTo(methodOn(CategoriaController.class).listarTodos()).withRel("Lista de Categorias"));
					return ResponseEntity.ok(c);
				 }).orElse(ResponseEntity.notFound().build());
	}
	
	
	/**
	 * Altera os dados de uma categoria
	 */
	@PutMapping("/{idCategoria}")
	@CacheEvict(value = "listaCategorias")
	@Operation(summary = "Atualiza uma categoria")
	public ResponseEntity<CategoriaDto> atualizar(@PathVariable Long idCategoria, @RequestBody @Valid CategoriaModel categoriaModel){
		return categoriaService.atualizar(idCategoria, categoriaModel)
				.map(c -> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build());
		
	}
	
	
	/*
	 * Deleta uma categoria
	 */
	@DeleteMapping("/{idCategoria}")
	@CacheEvict(value = "listaCategorias")
	@Operation(summary = "Deleta uma categoria")
	public ResponseEntity<?> deletarCategoria(@PathVariable Long idCategoria){
		return new ResponseEntity<>(categoriaService.deletar(idCategoria));
	}
}


