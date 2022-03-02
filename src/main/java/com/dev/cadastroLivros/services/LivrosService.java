package com.dev.cadastroLivros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.cadastroLivros.model.LivrosModel;
import com.dev.cadastroLivros.repositories.LivrosRepository;


@Service
public class LivrosService {

	@Autowired
	LivrosRepository livrosRepository;

	public Optional<LivrosModel> findById(Long id) {
		return livrosRepository.findById(id);
	}

	public Object save(LivrosModel livros) {
		return livrosRepository.save(livros);
	}

	public List <LivrosModel> findAll() {
		return livrosRepository.findAll();
	}

	public void delete(LivrosModel livrosModel) {
		livrosRepository.delete(livrosModel);

	}
}
