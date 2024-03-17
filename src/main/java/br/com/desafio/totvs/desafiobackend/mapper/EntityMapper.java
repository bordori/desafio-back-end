package br.com.desafio.totvs.desafiobackend.mapper;

import java.util.List;

/**
 * Interface para mapeamento de entidades
 * @param <ENTITY> entidade
 * @param <DTO> dto
 */
public interface EntityMapper<ENTITY, DTO> {

    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
    List<ENTITY> toListEntity(List<DTO> dto);
    List<DTO> toListDto(List<ENTITY> entity);
}
