package br.com.gandata.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "clientes",
		indexes = {@Index(name="id_cliente", columnList = "id")},
		uniqueConstraints = {
				@UniqueConstraint(name = "cpf_unique", columnNames = "cpf"),
				@UniqueConstraint(name = "email_unique", columnNames = "email")
		}
	)
@Schema( name = "Cliente")
@Audited
public class ClienteModel extends RepresentationModel<ClienteModel>{

	public ClienteModel() {}
	
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter @Setter
	@Size(max = 70, message = "O nome não pode ter mais que 70 caracteres")
	@NotBlank(message = "* O Nome do cliente não pode ser vazio")
	private String nome;
	
	@Getter @Setter
	@Column(length = 11)
	@CPF(message = "Informe um CPF válido")
	@NotBlank(message = "CPF não pode ser vazio")
	private String cpf;
	
	@Getter @Setter
	@Email
	private String email;
	
	@Getter @Setter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	// Mapper para sobrescrita de valores
	public void mapper(ClienteModel cliente) {
		if(cliente.getId() !=null) this.id = cliente.getId();
		if(cliente.getNome()!=null) this.nome= cliente.getNome();
		if(cliente.getCpf()!=null) this.cpf = cliente.getCpf().replaceAll("\\.", "").replaceAll("-", "");
		if(cliente.getEmail()!=null) this.email=cliente.getEmail();
		if(cliente.getDataNascimento()!=null) this.dataNascimento = cliente.getDataNascimento();
	}
	
}
