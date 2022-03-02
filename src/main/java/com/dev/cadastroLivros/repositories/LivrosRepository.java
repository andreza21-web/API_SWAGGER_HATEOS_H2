package com.dev.cadastroLivros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.cadastroLivros.model.LivrosModel;

public interface LivrosRepository extends JpaRepository<LivrosModel, Long> {

}

