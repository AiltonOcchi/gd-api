package br.com.gandata.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.gandata.api.service.ClienteService;

@ActiveProfiles("dev")
@SpringBootTest
class GlandataApiApplicationTests {
	
	@Autowired
	private ClienteService clienteService;

	@Test
	void deveriaEncontrarClienteId1() {
		assertTrue(clienteService.buscarPorId(1L).isPresent());
	}

}
