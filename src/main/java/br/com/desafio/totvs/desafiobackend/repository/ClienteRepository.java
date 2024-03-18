package br.com.desafio.totvs.desafiobackend.repository;

import br.com.desafio.totvs.desafiobackend.model.Cliente;
import br.com.desafio.totvs.desafiobackend.model.TelefoneCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para cliente
 * para operações de banco de dados
 * {@link JpaRepository}
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public Optional<List<Cliente>> findByNomeContainingIgnoreCase(String nome);
}
