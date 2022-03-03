package com.dev.cadastroLivros.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dev.cadastroLivros.model.LivrosModel;
import com.dev.cadastroLivros.repositories.LivrosRepository;
import com.dev.cadastroLivros.services.LivrosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LivrosTesteEndpoint {

	@MockBean
	private LivrosService service;

	@MockBean
	private LivrosRepository repository;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testeListLivros_sucess_status200() throws Exception {
		// cria um array com livros
		List<LivrosModel> listLivros = new ArrayList<>();
		listLivros.add(new LivrosModel((long) 1, "rainha", "vitoria", "12345", "romance", null));
		listLivros.add(new LivrosModel((long) 2, "harry potter", "autora", "99822", "fantasia", null));
		listLivros.add(new LivrosModel((long) 3, "orgulho", "jane austen", "96425", "romance", null));
		listLivros.add(new LivrosModel((long) 4, "mil", "claudia", "87549", "romance", null));
		listLivros.add(new LivrosModel((long) 5, "em chamas", "kiera kass", "778541", "romance", null));
		listLivros.add(new LivrosModel((long) 6, "caraval", "stephanie", "96584", "romance", null));

		// busca todos os livros
		Mockito.when(service.findAll()).thenReturn(listLivros);

		String url = "/livros";

		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

		String atualJsonResponse = mvcResult.getResponse().getContentAsString();
		System.out.println(atualJsonResponse);

		// transforma o array
		String expectJsonResponse = objectMapper.writeValueAsString(listLivros);

		System.out.println(expectJsonResponse);

		// compara a resposta
		assertThat(atualJsonResponse).isEqualTo(expectJsonResponse);

	}

	@Test
	void testeDeleteLivros_sucess_status200() throws Exception {

		// deleta um id

		Long livroId = 1L;
		Mockito.doNothing().when(service).delete(livroId);

		String url = "/livros/" + livroId;

		mockMvc.perform(delete(url)).andExpect(status().isOk()).andReturn();

		Mockito.verify(service, times(1)).delete(livroId);

	}

}
