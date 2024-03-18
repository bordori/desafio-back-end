package br.com.desafio.totvs.desafiobackend.repository;

import br.com.desafio.totvs.desafiobackend.model.TelefoneCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para telefone do cliente
 * para operações de banco de dados
 * {@link JpaRepository}
 */
public interface TelefoneClienteRepository extends JpaRepository<TelefoneCliente, Long> {

    /**
     * Metodo para buscar telefone por número
     * @param telefone número do telefone
     * @return lista de {@link TelefoneCliente}
     */
    Optional<List<TelefoneCliente>> findByTelefone(String telefone);

}
