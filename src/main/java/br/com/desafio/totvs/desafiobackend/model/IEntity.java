package br.com.desafio.totvs.desafiobackend.model;

/**
 * Interface para representação de entidades
 * @param <PK_TYPE> tipo da chave primária
 */
public interface IEntity<PK_TYPE> {

    /**
     * Metodo para obter a chave primária
     * @return chave primária
     */
    PK_TYPE getId();

    /**
     * Metodo para definir a chave primária
     * @param id chave primária
     */
    void setId(PK_TYPE id);
}
