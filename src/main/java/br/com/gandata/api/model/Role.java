package br.com.gandata.api.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
		name = "roles",
		indexes = {@Index(name="nome_role", columnList = "nomeRole")}
		)
@Schema( name = "Role")
public class Role implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	@Id
	private String nomeRole;
	
	@Getter @Setter
	@ManyToMany(mappedBy = "roles")
	private List<Usuario> usuarios;

	@Override
	public String getAuthority() {
		return this.nomeRole;
	}

}
