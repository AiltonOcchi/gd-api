package br.com.gandata.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gandata.api.dto.ProdutoDto;
import br.com.gandata.api.dto.ProdutoProjecao;
import br.com.gandata.api.model.ProdutoModel;
import br.com.gandata.api.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	/*
	 * Lista todos os produtos
	 */
	public List<ProdutoDto> listarTodos() {
		return produtoRepository.findAll().stream()
				.map(p -> modelMapper.map(p, ProdutoDto.class))
				.collect(Collectors.toList());
	}

	
	/*
	 * Lista todos os produtos com paginação de registros
	 */
	public Page<ProdutoDto> listarPaginado(Pageable pageable) {
		return produtoRepository.findAll(pageable)
				.map(p -> modelMapper.map(p, ProdutoDto.class));
	}
	
	
	/*
	 * Busca um produto por id 
	 */
	public Optional<ProdutoDto> buscarPorId(Long idProduto) {
		return produtoRepository.findById(idProduto).map(p -> modelMapper.map(p, ProdutoDto.class));
	}
	
	
	/*
	 * Busca um produto pelo nome 
	 */
	public Optional<ProdutoModel> buscaPorNome(String nome) {
		return produtoRepository.findByNome(nome);
	}

	
	/*
	 * Atualiza um produto a partir dos atributos informados (todos diferentes de nullo)
	 */
	public Optional<ProdutoDto> atualizar(Long idProduto, ProdutoModel produtoRequest) {
		return this.buscarPorId(idProduto).map(p -> {
			ProdutoModel produto = modelMapper.map(p, ProdutoModel.class);
			produto.mapper(produtoRequest);
			produtoRepository.save(produto);
			return Optional.of(modelMapper.map(produto, ProdutoDto.class));
		}).orElse(Optional.empty());
		
	}
	
	/*
	 * Adiciona um produto
	 */
	public ProdutoModel adicionar(ProdutoModel produtoModel) {
		return produtoRepository.save(produtoModel);
	}

	
	/*
	 * Deleta um produto
	 */
	public Boolean deletar(Long idProduto) {
		return produtoRepository.findById(idProduto).map(p -> {
			produtoRepository.delete(p);
			return true;
		}).orElse(false);
	}
	
	
	/*
	 * Lista todos os produtos utilizando Projecao
	 */
	public List<ProdutoProjecao> listarProdutosProjecao() {
		return produtoRepository.findProdutosProjecao();
	}
}
