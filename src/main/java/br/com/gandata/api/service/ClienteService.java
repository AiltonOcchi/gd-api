package br.com.gandata.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.gandata.api.model.ClienteModel;
import br.com.gandata.api.repository.ClienteRepository;
import br.com.gandata.api.specification.SpecificationCliente;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	/*
	 * Lista todos os clientes
	 */
	public List<ClienteModel> listarTodos() {
		return clienteRepository.findAll();
	}
	
	/*
	 * Lista todos os clientes com example of
	 */
	public List<ClienteModel> listarTodos(ClienteModel cliente) {
		return clienteRepository.findAll(Example.of(cliente));
	}
	
	/*
	 * Lista todos os clientes com example of
	 */
	public List<ClienteModel> listarComSpecification(ClienteModel cliente) {
		return clienteRepository.findAll(Specification
				.where(
						SpecificationCliente.nomeLike(cliente.getNome())
						.or(SpecificationCliente.dataNascimento(cliente.getDataNascimento()))
						));
	}

	
	/*
	 * Lista todos os clientes com paginação de registros
	 */
	public Page<ClienteModel> listarPaginado(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
	
	
	/*
	 * Busca um cliente por id 
	 */
	public Optional<ClienteModel> buscarPorId(Long idCliente) {
		return clienteRepository.findById(idCliente);
	}

	
	/*
	 * Busca um cliente pelo e-mail ou pelo cpf 
	 */
	public Optional<ClienteModel> buscaPorEmailOuCpf(String email, String cpf) {
		return clienteRepository.findByEmailOrCpf(email, cpf);
	}
	
	
	/*
	 * Atualiza um cliente sobrescrevendo os atributos diferentes de nullo
	 */
	public Optional<ClienteModel> atualizar(Long idCliente, ClienteModel clienteModel) {
		return this.buscarPorId(idCliente).map(c -> {
			c.mapper(clienteModel);
			return clienteRepository.save(c);
		});
	}

	
	/*
	 * Adiciona um cliente
	 */
	public ClienteModel adicionar(ClienteModel clienteModel) {
		clienteModel.setCpf(clienteModel.getCpf().replaceAll("\\.", "").replaceAll("-", ""));
		return clienteRepository.save(clienteModel);
	}

	
	/*
	 * Deleta um cliente
	 */
	public Boolean deletar(Long idCliente) {
		return this.buscarPorId(idCliente).map(c -> {
			clienteRepository.delete(c);
			return true;
		}).orElse(false);
	}
	
	/*
	 * Lista as revisões de auditoria do cliente
	 */
	public List<String> listaRevisoes(Long idCliente){
		return clienteRepository.findRevisions(idCliente)
				.stream().map(Object::toString)
				.collect(Collectors.toList());
	}
	
}
