package br.com.desafio.totvs.desafiobackend.service;

import br.com.desafio.totvs.desafiobackend.model.IEntity;

import java.util.List;

/**
 * Interface para serviço genérico
 * para operações de banco de dados e regras de negócio
 * @param <ENTITY> entidade
 * @param <PK_TYPE> tipo da chave primária
 */
public interface GenericService<ENTITY extends IEntity<PK_TYPE>, PK_TYPE> {

    /**
     * Inclui uma entidade
     * @param entity
     * @return entidade incluída
     */
    ENTITY incluir(ENTITY entity);

    /**
     * Altera uma entidade
     * @param entity
     * @return entidade alterada
     */
    ENTITY alterar(ENTITY entity);

    /**
     * Exclui uma entidade
     * @param id
     */
    void excluir(PK_TYPE id);

    /**
     * Obtem uma entidade por id
     * @param id
     * @return entidade
     */
    ENTITY obterPorId(PK_TYPE id);

    /**
     * Obtem todas as entidades
     * @return lista de entidades
     */
    List<ENTITY> obterTodos();

}
