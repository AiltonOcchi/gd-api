package br.com.gandata.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

import br.com.gandata.api.model.ProdutoModel;

@Repository
public class ProdutoCustomRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<ProdutoModel> buscarPorParametros(String nomeProduto, String nomeCategoria) {
		
		String jpql = "SELECT p FROM ProdutoModel p  WHERE 1=1";
		
		if (!Strings.isNullOrEmpty(nomeProduto))jpql += "AND LOWER(p.nome) LIKE :nomeProduto ";
		if (!Strings.isNullOrEmpty(nomeCategoria))jpql += "AND LOWER(p.categoria.nome) LIKE :nomeCategoria ";
				
		TypedQuery<ProdutoModel> query = em.createQuery(jpql, ProdutoModel.class);
		
		if (!Strings.isNullOrEmpty(nomeProduto)) query.setParameter("nomeProduto", "%"+nomeProduto.toLowerCase()+"%");
		if (!Strings.isNullOrEmpty(nomeCategoria)) query.setParameter("nomeCategoria", "%"+nomeCategoria.toLowerCase()+"%");
		
		return query.getResultList();
		
	}

}
