package br.com.gandata.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import br.com.gandata.api.model.ClienteModel;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long>, RevisionRepository<ClienteModel, Long, Long>{
	
	Optional<ClienteModel>findByEmailOrCpf(String email, String cpf);
	
	Optional<ClienteModel>findByNome(String nome);

}
