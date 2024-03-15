package br.com.desafio.totvs.desafiobackend.mapper;

import org.mapstruct.Mapper;

public interface EntityMapper<O, D> {

    D toDto(O entity);
    O toEntity(D dto);
}
