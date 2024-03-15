package br.com.desafio.totvs.desafiobackend.service;

import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import br.com.desafio.totvs.desafiobackend.model.IEntity;

import java.util.List;

public interface GenericService<ENTITY extends IEntity<PK_TYPE>, PK_TYPE> {

    ENTITY incluir(ENTITY entity) throws BusinessException;

    ENTITY alterar(ENTITY entity) throws BusinessException;

    void excluir(PK_TYPE id);

    ENTITY obterPorId(PK_TYPE id);

    List<ENTITY> obterTodos();

}
