package br.com.gandata.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gandata.api.model.ClienteModel;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long>{
	
	Optional<ClienteModel>findByEmailOrCpf(String email, String cpf);

}
