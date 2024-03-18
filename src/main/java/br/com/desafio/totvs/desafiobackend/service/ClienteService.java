package br.com.desafio.totvs.desafiobackend.service;

import br.com.desafio.totvs.desafiobackend.model.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Interface para serviço de cliente
 * para operações de banco de dados e regras de negócio
 * {@link GenericService}
 */
public interface ClienteService extends GenericService<Cliente, Long> {

    Optional<List<Cliente>> obterPorNome(String nome);
}
