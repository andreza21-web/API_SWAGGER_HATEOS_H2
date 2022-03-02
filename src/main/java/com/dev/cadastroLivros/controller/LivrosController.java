package com.dev.cadastroLivros.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.cadastroLivros.dto.LivrosDto;
import com.dev.cadastroLivros.model.LivrosModel;
import com.dev.cadastroLivros.services.LivrosService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "livros")
@RestController
@RequestMapping("/livros")
public class LivrosController {
	@Autowired
	LivrosService service;

	@ApiOperation(value = "Listar por Código")
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneLibraries(@PathVariable(value = "id") Long id) {
		Optional<LivrosModel> librariesModel = service.findById(id);
		if (!librariesModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("libraries not found.");
		}
		librariesModel.get().add(linkTo(methodOn(LivrosController.class).getAllLivros()).withRel("book list"));
		return ResponseEntity.status(HttpStatus.OK).body(librariesModel.get());
	}

	@ApiOperation(value = "Listar Todos")
	@GetMapping
	public ResponseEntity<List<LivrosModel>> getAllLivros() {
		List<LivrosModel> librariesList = service.findAll();
		if (librariesList.isEmpty()) {
			return new ResponseEntity<List<LivrosModel>>(HttpStatus.NOT_FOUND);
		} else {
			for (LivrosModel libraries : librariesList) {
				long id = libraries.getId();
				libraries.add(linkTo(methodOn(LivrosController.class).getOneLibraries(id)).withSelfRel());

			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@ApiOperation(value = "Salvar livro")
	@PostMapping
	public ResponseEntity<Object> saveLibraries(@Valid @RequestBody LivrosDto livrosDto) {
		LivrosModel livrosModel = new LivrosModel();
		BeanUtils.copyProperties(livrosDto, livrosModel);
		livrosModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(livrosModel));

	}

	@ApiOperation(value = "Atualizar livro")
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateLibrarie(@PathVariable(value = "id") Long id,
			@RequestBody @Valid LivrosDto livrosDto) {
		Optional<LivrosModel> librariesModel = service.findById(id);
		if (!librariesModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libraries not found.");
		}
		LivrosModel livrosModel = new LivrosModel();
		BeanUtils.copyProperties(livrosDto, livrosModel);
		livrosModel.setId(livrosModel.getId());
		livrosModel.setRegistrationDate(livrosModel.getRegistrationDate());
		return ResponseEntity.status(HttpStatus.OK).body(service.save(livrosModel));
	}

	@ApiOperation(value = "Deletar livro")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
		Optional<LivrosModel> livrosModel = service.findById(id);
		if (!livrosModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libraries not found.");
		}
		service.delete(livrosModel.get());
		return ResponseEntity.status(HttpStatus.OK).body("Libraries deleted successfully.");
	}
}
