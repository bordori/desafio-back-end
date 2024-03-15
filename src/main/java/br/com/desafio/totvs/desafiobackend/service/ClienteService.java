package br.com.desafio.totvs.desafiobackend.service;

import br.com.desafio.totvs.desafiobackend.model.Cliente;

import java.util.List;

public interface ClienteService extends GenericService<Cliente, Long> {

    public List<Cliente> obterPorNome(String nome);
}
