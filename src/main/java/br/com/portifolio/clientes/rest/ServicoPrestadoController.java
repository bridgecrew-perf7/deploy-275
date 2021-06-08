package br.com.portifolio.clientes.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.portifolio.clientes.model.entity.Cliente;
import br.com.portifolio.clientes.model.entity.ServicoPrestado;
import br.com.portifolio.clientes.model.repository.ClienteRepository;
import br.com.portifolio.clientes.model.repository.ServicoPrestadoRepository;
import br.com.portifolio.clientes.rest.dto.ServicoPrestadoDTO;
import br.com.portifolio.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {
	
	private final ClienteRepository clienteRepository;
	private final ServicoPrestadoRepository servicoPrestadoRepository;	
	private final BigDecimalConverter bigDecimalConverter;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody ServicoPrestadoDTO dto) {
		
		LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Integer idCliente = dto.getIdCliente();
		
		Cliente cliente = clienteRepository
				.findById(idCliente)
				.orElseThrow(() -> 
					new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Cliente não existe"));
		
		//Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
		//or Else pega o cliente dentro se não tiver retorna um novo
		//Cliente cliente = clienteOptional.orElse(new Cliente());
		
		//se não houver cliente dentro do optional da exceção
		//Cliente cliente = clienteOptional.get();
		
		//Map da para pegar um elemento do option Obs: retorna outro optional
		//Optional<String> nomeOptinal =  clienteOptional.map(c -> c.getNome());
		
		//existe ifPresent Aula 75
		
		
		ServicoPrestado servicoPrestado = new ServicoPrestado();
		servicoPrestado.setDescricao( dto.getDescricao() );
		servicoPrestado.setData( data );
		servicoPrestado.setCliente( cliente );
		servicoPrestado.setValor( bigDecimalConverter.converter( dto.getPreco() ) );
		
		return servicoPrestadoRepository.save(servicoPrestado);
	}
	
	@GetMapping
	public List<ServicoPrestado> pesquisar(
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(value = "mes", required = false ) Integer mes
	){		
		
		return servicoPrestadoRepository.findByClienteAndMes("%" + nome + "%", mes);
	}
}
