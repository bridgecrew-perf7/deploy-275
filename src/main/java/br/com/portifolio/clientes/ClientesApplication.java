package br.com.portifolio.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.portifolio.clientes.model.entity.Cliente;
import br.com.portifolio.clientes.model.repository.ClienteRepository;

/**
 *
 * Spring Boot application starter class
 */
@SpringBootApplication
public class ClientesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientesApplication.class, args);
    }
}
