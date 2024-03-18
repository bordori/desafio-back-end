package br.com.desafio.totvs.desafiobackend.service.impl;

import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import br.com.desafio.totvs.desafiobackend.model.TelefoneCliente;
import br.com.desafio.totvs.desafiobackend.repository.TelefoneClienteRepository;
import br.com.desafio.totvs.desafiobackend.service.TelefoneClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do serviço de telefone do cliente
 * para validações de campos e regras de negócio
 * e operações de banco de dados
 * implementando {@link TelefoneClienteService}
 */
@Service
public class TelefoneClienteServiceImpl implements TelefoneClienteService {

    @Autowired
    TelefoneClienteRepository repository;

    @Override
    public Optional<List<TelefoneCliente>> findByTelefone(String telefone) {
        return repository.findByTelefone(telefone);
    }
}
