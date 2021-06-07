package br.com.portifolio.clientes.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.portifolio.clientes.model.entity.Cliente;
import br.com.portifolio.clientes.model.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("http://localhost:4200")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid Cliente cliente) {		
		return repository.save(cliente);
	}
	
	@GetMapping
	public List<Cliente> obterTodos(){
		return repository.findAll();
	}
	
	@GetMapping("{id}")
	public Cliente acharPorId(@PathVariable("id") Integer id) {
		return repository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable("id") Integer id) {
//		repository.deleteById(id);
		
		//essa abordagem, possibilita saber se o cliente que tentou deletar existe no banco
		repository
			.findById(id)
			.map(cliente -> {
				repository.delete(cliente);
				return Void.TYPE;
			})
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Cliente clienteAtualizado) {
		//essa abordagem, possibilita saber se o cliente que tentou atualizar existe no banco
		repository
			.findById(id)
			.map(cliente -> {
				clienteAtualizado.setId(cliente.getId());
				clienteAtualizado.setDataCadastro(cliente.getDataCadastro());
				return repository.save(clienteAtualizado);
				
			})
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Cliente não encontrado"));
	}
}
