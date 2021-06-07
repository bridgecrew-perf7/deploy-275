package br.com.portifolio.clientes.model.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.portifolio.clientes.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
