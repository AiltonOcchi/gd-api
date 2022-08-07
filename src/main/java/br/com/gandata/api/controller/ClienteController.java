package br.com.gandata.api.controller;

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

import br.com.gandata.api.model.ClienteModel;
import br.com.gandata.api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gerenciamento dos clientes")
@SecurityRequirement(name = "Authorization")// Exige na Open API authenticação de todos os endpoints desta classe
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	/**
	 * Lista todos os clientes
	 */
	@GetMapping("")
	@Operation(summary = "Lista todas os clientes cadastrados")
	public ResponseEntity<List<ClienteModel>> listarTodos(){
		return ResponseEntity.ok(clienteService.listarTodos());
	}
	
	
	/**
	 * Insere um novo cliente caso ele não exista
	 */
	@PostMapping("")
	@Operation(summary = "Cadastra um novo cliente (com validação)")
	public ResponseEntity<?> inserir(@RequestBody @Valid ClienteModel clienteModel){
		Optional<ClienteModel> cliente = clienteService.buscaPorEmailOuCpf(clienteModel.getEmail(), clienteModel.getCpf());
		if(cliente.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(clienteService.adicionar(clienteModel));
	}
	
	
	/**
	 * Lista todos os clientes com paginação de registros
	 */
	@GetMapping("/listarPaginado")
	@Operation(summary = "Lista todas os clientes cadastrados com paginação de registros")
	public ResponseEntity<Page<ClienteModel>> listarPaginado(
			@PageableDefault(page = 0, size = 2, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok(clienteService.listarPaginado(pageable));
	}
	
	
	/**
	 * Busca um cliente pelo id
	 */
	@GetMapping("/{idCliente}")
	@Operation(summary = "Busca um cliente a partir do ID")
	public ResponseEntity<ClienteModel> buscarPorId(@PathVariable Long idCliente){
		return clienteService.buscarPorId(idCliente)
				.map(c -> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build());
	}
	
	
	/**
	 * Altera os dados de um cliente a partir dos atributos informados
	 * Todos atributos diferentes de nullo serão atuailizado, neste caso nao usar o @Valid
	 */
	@PutMapping("/{idCliente}")
	@Operation(summary = "Atualiza um cliente")
	public ResponseEntity<ClienteModel> atualizar(@PathVariable Long idCliente, @RequestBody ClienteModel clienteModel){
		return clienteService.atualizar(idCliente, clienteModel)
				.map(c -> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build());
	}
	
	
	/*
	 * Deleta um cliente
	 */
	@DeleteMapping("/{idCliente}")
	@Operation(summary = "Deleta um cliente")
	public ResponseEntity<?> deletarCliente(@PathVariable Long idCliente){
		return clienteService.deletar(idCliente) ? 
				ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}


