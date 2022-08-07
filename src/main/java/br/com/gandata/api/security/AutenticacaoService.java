package br.com.gandata.api.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.gandata.api.model.Usuario;
import br.com.gandata.api.repository.UsuarioRepository;

@Repository
@Transactional
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * carrega usuario através do login e devolve para o Spring Security validar
	 */
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByLoginIgnoreCase(login);
		if (usuario.isPresent()) {
            return usuario.get();
		}
		throw new UsernameNotFoundException("Dados inválidos");
	}

}
