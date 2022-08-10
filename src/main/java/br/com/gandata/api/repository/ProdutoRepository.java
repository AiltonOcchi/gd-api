package br.com.gandata.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gandata.api.model.ProdutoModel;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long>{
	
	Optional<ProdutoModel> findByNome(String nome);
	
	@Query("SELECT p FROM ProdutoModel p JOIN FETCH  p.categoria")
	List<ProdutoModel> findAll();

}
