package br.com.gandata.api.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.gandata.api.model.ClienteModel;

public class SpecificationCliente {
	
	public static Specification<ClienteModel> nomeLike(String nome){
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
	}
	
	
	public static Specification<ClienteModel> dataNascimento(LocalDate dataNascimento){
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.equal(root.get("dataNascimento"), dataNascimento);
	}

}
