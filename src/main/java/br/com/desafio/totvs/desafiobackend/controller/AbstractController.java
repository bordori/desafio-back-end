package br.com.desafio.totvs.desafiobackend.controller;

import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import br.com.desafio.totvs.desafiobackend.mapper.EntityMapper;
import br.com.desafio.totvs.desafiobackend.model.IEntity;
import br.com.desafio.totvs.desafiobackend.service.GenericService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractController<ENTITY extends IEntity<PK_TYPE>,
        DTO_ENTITY,
        PK_TYPE,
        SERVICE extends GenericService<ENTITY, PK_TYPE>,
        MAPPER extends EntityMapper<ENTITY, DTO_ENTITY>        > {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected SERVICE service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected MAPPER mapper;


    @PostMapping("/incluir")
    public DTO_ENTITY incluir( @RequestBody DTO_ENTITY dtoEntity) throws BusinessException {
        ENTITY entity = mapper.toEntity(dtoEntity);
        return mapper.toDto(service.incluir(entity));
    }

    @PutMapping("/alterar")
    public DTO_ENTITY alterar(@Valid @RequestBody DTO_ENTITY dtoEntity) throws BusinessException {
        ENTITY entity = mapper.toEntity(dtoEntity);
        return mapper.toDto(service.alterar(entity));
    }

    public void excluir(PK_TYPE id) {
        service.excluir(id);
    }

    public DTO_ENTITY obterPorId(PK_TYPE id) {
        return mapper.toDto(service.obterPorId(id));
    }

    public List<DTO_ENTITY> obterTodos() {
        return null;
    }
}
