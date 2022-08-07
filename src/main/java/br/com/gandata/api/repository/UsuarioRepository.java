package br.com.gandata.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gandata.api.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, String>{
	
	Optional<Usuario> findByLoginIgnoreCase(String login);

}