package br.com.gandata.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gandata.api.dto.ProdutoProjecao;
import br.com.gandata.api.model.ProdutoModel;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long>{
	
	Optional<ProdutoModel> findByNome(String nome);
	
	@Query("SELECT p FROM ProdutoModel p JOIN FETCH  p.categoria")
	List<ProdutoModel> findAll();
	
	//@Query(value="SELECT p.nome, p.descricao, p.preco, c.nome as categoria from produtos p inner join categorias c on p.categoria_id = c.id", nativeQuery = true)
	@Query(value="SELECT p.nome as nome, p.descricao as descricao, p.preco as preco, p.categoria.nome as categoria from ProdutoModel p ")
	List<ProdutoProjecao> findProdutosProjecao();

}
