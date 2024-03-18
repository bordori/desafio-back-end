package br.com.desafio.totvs.desafiobackend.controller;

import br.com.desafio.totvs.desafiobackend.mapper.EntityMapper;
import br.com.desafio.totvs.desafiobackend.model.IEntity;
import br.com.desafio.totvs.desafiobackend.service.GenericService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata para consultas e operações básicas api rest
 * @param <ENTITY> entidade estendida de {@link IEntity}
 * @param <DTO_ENTITY> dto da entidade
 * @param <PK_TYPE> tipo da chave primária
 * @param <SERVICE> serviço da entidade
 * @param <MAPPER> mapeador da entidade
 */
public abstract class AbstractController<ENTITY extends IEntity<PK_TYPE>,
        DTO_ENTITY,
        PK_TYPE,
        SERVICE extends GenericService<ENTITY, PK_TYPE>,
        MAPPER extends EntityMapper<ENTITY, DTO_ENTITY>> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected SERVICE service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected MAPPER mapper;


    /**
     * Método para inclusão de entidade
     * @param dtoEntity dto da entidade
     * @return dtoEnity da entidade
     */
    @PostMapping("/incluir")
    public ResponseEntity<DTO_ENTITY> incluir(@RequestBody DTO_ENTITY dtoEntity) {
        ENTITY entity = mapper.toEntity(dtoEntity);
        return ResponseEntity.ok(mapper.toDto(service.incluir(entity)));
    }

    /**
     * Método para alteração de entidade
     * @param dtoEntity dto da entidade
     * @return dtoEntity da entidade
     */
    @PutMapping("/alterar")
    public ResponseEntity<DTO_ENTITY> alterar(@Valid @RequestBody DTO_ENTITY dtoEntity) {
        ENTITY entity = mapper.toEntity(dtoEntity);
        return ResponseEntity.ok(mapper.toDto(service.alterar(entity)));
    }

    /**
     * Método para exclusão de entidade
     * @param id chave primária da entidade
     */
    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable(name = "id") PK_TYPE id) {
        service.excluir(id);
    }

    /**
     * Método para obter entidade por id
     * @param id chave primária da entidade
     * @return dto da entidade
     */
    @GetMapping("/{id}")
    public ResponseEntity<DTO_ENTITY> obterPorId(@PathVariable(name = "id") PK_TYPE id) {
        return ResponseEntity.ok(mapper.toDto(service.obterPorId(id)));
    }

    /**
     * Método para obter todas as entidades
     * @return lista de dto da entidade
     */
    @GetMapping()
    public ResponseEntity<List<DTO_ENTITY>> obterTodos() {
        return ResponseEntity.ok(mapper.toListDto(service.obterTodos().orElse(new ArrayList<>())));
    }
}
