package br.com.desafio.totvs.desafiobackend.service;

import br.com.desafio.totvs.desafiobackend.model.TelefoneCliente;

import java.util.List;
import java.util.Optional;

/**
 * Interface para serviço de telefone do cliente
 * para operações de banco de dados e regras de negócio
 */
public interface TelefoneClienteService {

    /**
     * Metodo para buscar telefone por número
     * @param telefone número do telefone
     * @return lista de {@link TelefoneCliente}
     */
    Optional<List<TelefoneCliente>> findByTelefone(String telefone);

}
