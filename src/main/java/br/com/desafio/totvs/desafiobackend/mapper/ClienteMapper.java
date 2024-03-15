package br.com.desafio.totvs.desafiobackend.mapper;

import br.com.desafio.totvs.desafiobackend.dto.ClienteDto;
import br.com.desafio.totvs.desafiobackend.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<Cliente, ClienteDto> {
}
