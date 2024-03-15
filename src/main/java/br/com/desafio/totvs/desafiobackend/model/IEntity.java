package br.com.desafio.totvs.desafiobackend.model;

public interface IEntity<PK_TYPE> {

    PK_TYPE getId();
    void setId(PK_TYPE id);
}
