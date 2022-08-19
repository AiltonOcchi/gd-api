package br.com.gandata.api.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void autenticacaoIncorretaDeveriaRetornarErro400() throws Exception {
		
		URI uri = new URI("/auth");
		String json = "{\"login\":\"teste\",\"senha\":\"54321\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
	}
	
	
	@Test
	void autenticacaoCorretaDeveriaRetornarStatus200() throws Exception {
		
		URI uri = new URI("/auth");
		String json = "{\"login\":\"maria\",\"senha\":\"123\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200));
	}

}
