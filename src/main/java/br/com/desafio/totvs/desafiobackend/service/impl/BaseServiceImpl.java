package br.com.desafio.totvs.desafiobackend.service.impl;

import br.com.desafio.totvs.desafiobackend.enums.GenericMessageError;
import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import br.com.desafio.totvs.desafiobackend.model.IEntity;
import br.com.desafio.totvs.desafiobackend.service.GenericService;
import br.com.desafio.totvs.desafiobackend.util.Util;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Classe base para implementação de serviços
 * com métodos comuns para operações de banco de dados
 * @param <ENTITY> entidade
 * @param <PK_TYPE> tipo da chave primária
 * @param <REPOSITORY> repositório {@link JpaRepository
 */
public abstract class BaseServiceImpl<ENTITY extends IEntity<PK_TYPE>,
        PK_TYPE, REPOSITORY extends JpaRepository<ENTITY, PK_TYPE>> implements GenericService<ENTITY, PK_TYPE> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public REPOSITORY repository;

    /**
     * Método para incluir um registro
     * com validações de campos e regras de negócio
     * @param entity entidade {@link ENTITY}
     * @return entidade incluída {@link ENTITY}
     */
    @Override
    public ENTITY incluir(ENTITY entity) {
        if (entity == null) {
            throw new BusinessException(GenericMessageError.REGISTRO_NAO_INFORMADO);
        }
        setListReference(entity);
        prepararDadosIncluirAlterar(entity);
        validarCampos(entity);
        validarRegrasNegocio(entity);
        try {
            return repository.save(entity);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new BusinessException(GenericMessageError.REGISTRO_ERRO_SALVAR_EDITAR);
        }
    }

    /**
     * Método para preparar os dados antes de incluir ou alterar
     * retirando espaços em branco dos campos
     * @param entity entidade {@link ENTITY}
     */
    public void prepararDadosIncluirAlterar(ENTITY entity) {
        retirarEspacosEmBranco(entity);
    }

    /**
     * Método para alterar um registro
     * com validações de campos e dados
     * @param entity entidade {@link ENTITY}
     * @return entidade alterada {@link ENTITY}
     */
    @Override
    public ENTITY alterar(ENTITY entity) {
        if (entity == null || entity.getId() == null) {
            throw new BusinessException(GenericMessageError.REGISTRO_NAO_INFORMADO);
        }

        setListReference(entity);
        prepararDadosIncluirAlterar(entity);
        validarCampos(entity);
        validarRegrasNegocio(entity);
        validaSeExisteRegistro(entity.getId());
        try {
            return repository.save(entity);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new BusinessException(GenericMessageError.REGISTRO_ERRO_SALVAR_EDITAR);
        }
    }

    /**
     * Método para excluir um registro
     * @param id chave primária
     */
    @Override
    public void excluir(PK_TYPE id) {
        if (id == null)
            throw new BusinessException(GenericMessageError.REGISTRO_NAO_INFORMADO);

        validaSeExisteRegistro(id);

        repository.deleteById(id);
    }

    /**
     * Método para obter um registro por id
     * @param id chave primária
     * @return entidade {@link ENTITY}
     */
    @Override
    public ENTITY obterPorId(PK_TYPE id) {
        if (id == null)
            throw new BusinessException(GenericMessageError.REGISTRO_NAO_INFORMADO);

        return repository.findById(id).orElseThrow(() -> new BusinessException(GenericMessageError.REGISTRO_NAO_ENCONTRADO));
    }

    /**
     * Método para obter todos os registros
     * @return lista de entidades {@link ENTITY}
     */
    @Override
    public List<ENTITY> obterTodos() {
        return repository.findAll();
    }

    /**
     * Método para validar se existe um registro
     * @param id chave primária
     */
    public void validaSeExisteRegistro(@NotNull PK_TYPE id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(GenericMessageError.REGISTRO_NAO_ENCONTRADO);
        }
    }

    /**
     * Método simples utilizando reflexão
     * para setar o objeto FK na lista de objetos
     * @param entity entidade {@link ENTITY}
     */
    private void setListReference(ENTITY entity) {
        List<Field> fieldList = Arrays.stream(entity.getClass().getDeclaredFields()).toList();

        for (Field field : fieldList) {
            if (!Collection.class.isAssignableFrom(field.getType()))
                continue;

            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

            var list = (Collection<?>) getFieldValue(entity, field.getName());


            if (list == null)
                continue;

            for (Object object : list) {
                List<Field> auxFieldList = Arrays.stream(object.getClass().getDeclaredFields()).toList();
                for (Field auxField : auxFieldList) {
                    if (auxField.getType().isAssignableFrom(entity.getClass())) {
                        setFieldValue(object, auxField.getName(), entity);
                    }
                }
            }
        }
    }

    /**
     * Método simples utilizando reflexão
     * para obter o valor de um campo
     * @param entity entidade {@link IEntity}
     * @param fieldName nome do campo a ser obtido
     * @return valor do campo
     */
    private Object getFieldValue(IEntity<PK_TYPE> entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            return entity.getClass().getMethod(methodName).invoke(entity);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método simples utilizando reflexão
     * para setar o valor de um campo
     * @param entity entidade {@link IEntity}
     * @param fieldName nome do campo a ser setado
     * @param value valor a ser setado
     */
    private void setFieldValue(Object entity, String fieldName, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            entity.getClass().getMethod(methodName, field.getType()).invoke(entity, value);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para retirar espaços em branco dos campos
     * de campos String
     * @param entity entidade {@link ENTITY}
     */
    public void retirarEspacosEmBranco(ENTITY entity) {
        List<Field> fieldList = Arrays.stream(entity.getClass().getDeclaredFields()).toList();
        for (Field field : fieldList) {
            if (String.class.isAssignableFrom(field.getType())) {
                String value = getFieldValue(entity, field.getName()).toString();
                if (value != null) {
                    value = Util.retiraEspacosVazios(value);
                    setFieldValue(entity, field.getName(), value);
                }
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                var list = (Collection<?>) getFieldValue(entity, field.getName());

                if (list == null)
                    continue;

                for (Object object : list) {
                    retirarEspacosEmBranco((ENTITY) object);
                }
            }
        }
    }

    /**
     * Método abstrato para validar campos obrigátorios da entidade
     * para ser implementado nas classes filhas
     * @param entity entidade {@link ENTITY}
     */
    public abstract void validarCampos(ENTITY entity);

    /**
     * Método abstrato para validar regras de negócio
     * para ser implementado nas classes filhas
     * @param entity entidade {@link ENTITY}
     */
    public abstract void validarRegrasNegocio(ENTITY entity);
}
