package br.com.gandata.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gandata.api.dto.CategoriaDto;
import br.com.gandata.api.model.CategoriaModel;
import br.com.gandata.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private ModelMapper modelMapper;

	
	@Autowired
	private CategoriaRepository categoriaRepository;

	/*
	 * Lista todos as categorias
	 */
	public List<CategoriaDto> listarTodos() {
		return categoriaRepository.findAll().stream()
				.map(c -> modelMapper.map(c, CategoriaDto.class)).collect(Collectors.toList());
	}
	
	
	/*
	 * Lista todos as categorias com paginação de registros
	 */
	public Page<CategoriaDto> listarPaginado(Pageable pageable) {
		return categoriaRepository.findAll(pageable)
				.map(c -> modelMapper.map(c, CategoriaDto.class));
	}
	
	
	/*
	 * Busca uma categoria pelo nome 
	 */
	public Optional<CategoriaModel> buscaPorNome(String nome) {
		return categoriaRepository.findByNome(nome);
	}

	
	/*
	 * Busca uma categoria por id atribuindo a um dto
	 */
	public Optional<CategoriaDto> buscarPorId(Long idCategoria) {
		return categoriaRepository.findById(idCategoria).map(c -> modelMapper.map(c, CategoriaDto.class));
	}
	
	/*
	 * Busca uma categoria por id atribuindo a um dto
	 */
	public Optional<CategoriaModel> buscaCategoriaPorId(Long idCategoria) {
		return categoriaRepository.findById(idCategoria);
	}

	
	/*
	 * Atualiza uma categoria
	 */
	public Optional<CategoriaDto> atualizar(Long id, CategoriaModel categoriaModel) {
		return this.buscarPorId(id).map(c -> {
			modelMapper.map(categoriaModel, c);
			categoriaRepository.save(modelMapper.map(c, CategoriaModel.class));
			return c;
		});
	}

	
	/*
	 * Adiciona uma categoria
	 */
	public CategoriaModel adicionar(CategoriaModel categoriaModel) {
		return categoriaRepository.save(categoriaModel);
	}

	
	/*
	 * Verifica se a categoria está vazia e deleta o registro	 
	 */
	public Boolean deletar(Long idCategoria) {
		return this.buscaCategoriaPorId(idCategoria).map(c -> {
			if(c.getProdutos().isEmpty()) {
				categoriaRepository.delete(c);
				return true;
			}return false;
		}).orElse(false);
	}
}
