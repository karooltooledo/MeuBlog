package br.org.generation.blogPessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogPessoal.model.Postagem;
import br.org.generation.blogPessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemrepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll (){
		return ResponseEntity.ok(postagemrepository.findAll());
	}
	
	/*@GetMapping("/{id}")	
 	public ResponseEntity<Postagem> getById(@PathVariable long id) {
		
	 Optional<Postagem> postagem = postagemrepository.findById(id);
	 if (postagem.isPresent()) {
		 return ResponseEntity.ok(postagem.get());
	 }
	 return ResponseEntity.notFound().build();  */
		 
	// Lambda basicamente significa usar uma função como argumento numa chamada à outra função.
	 @GetMapping("/{id}")	
	public ResponseEntity<Postagem> getByIdIfElse(@PathVariable long id) {
		return postagemrepository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	 //Busca o titulo na Postagem
	@GetMapping("/titulo/{titulo}")	
 	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemrepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	//Criar Postagem
	@PostMapping
 	public ResponseEntity<Postagem> postPostagem(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemrepository.save(postagem));
	}
	//Alterar Postagem
	@PutMapping	
 	public ResponseEntity<Postagem> putPostagem(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.OK).body(postagemrepository.save(postagem));
	}
	//Deletar Postagem
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		postagemrepository.deleteById(id);
	}
}

