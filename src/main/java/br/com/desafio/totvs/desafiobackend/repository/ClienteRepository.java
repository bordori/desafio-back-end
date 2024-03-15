package br.com.desafio.totvs.desafiobackend.repository;

import br.com.desafio.totvs.desafiobackend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
