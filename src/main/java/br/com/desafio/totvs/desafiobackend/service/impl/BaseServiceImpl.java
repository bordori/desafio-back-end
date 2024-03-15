package br.com.desafio.totvs.desafiobackend.service.impl;

import br.com.desafio.totvs.desafiobackend.enums.GenericMessageError;
import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import br.com.desafio.totvs.desafiobackend.model.Cliente;
import br.com.desafio.totvs.desafiobackend.model.IEntity;
import br.com.desafio.totvs.desafiobackend.model.TelefoneCliente;
import br.com.desafio.totvs.desafiobackend.service.GenericService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class BaseServiceImpl<ENTITY extends IEntity<PK_TYPE>,
        PK_TYPE, REPOSITORY extends JpaRepository<ENTITY, PK_TYPE>> implements GenericService<ENTITY, PK_TYPE> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected REPOSITORY repository;

    @Override
    public ENTITY incluir(ENTITY entity) throws BusinessException {
        if (entity == null) {
            throw new BusinessException(GenericMessageError.REGISTRO_NAO_INFORMADO);
        }
        setListReference(entity);
        validarCampos(entity);
        validarDados(entity);

        return repository.save(entity);
    }

    @Override
    public ENTITY alterar(ENTITY entity) throws BusinessException {
        if (entity == null || entity.getId() == null) {
            throw new BusinessException(GenericMessageError.REGISTRO_NAO_INFORMADO);
        }

        setListReference(entity);
        validarCampos(entity);
        validarDados(entity);
        validaSeExisteRegistro(entity.getId());
        return repository.save(entity);

    }

    @Override
    public void excluir(PK_TYPE id) {
        repository.deleteById(id);
    }

    @Override
    public ENTITY obterPorId(PK_TYPE id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ENTITY> obterTodos() {
        return repository.findAll();
    }

    protected abstract void validarCampos(ENTITY entity) throws BusinessException;
    protected abstract void validarDados(ENTITY entity) throws BusinessException;

    protected void validaSeExisteRegistro(@NotNull PK_TYPE id) throws BusinessException {
        if (!repository.existsById(id)) {
            throw new BusinessException(GenericMessageError.REGISTRO_NAO_ENCONTRADO);
        }
    }


    private void setListReference(ENTITY entity) {
        List<Field> fieldList = Arrays.stream(entity.getClass().getDeclaredFields()).toList();

        for (Field field : fieldList) {
            if (!Collection.class.isAssignableFrom(field.getType()))
                continue;

            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

            var list = (Collection<?>) getFieldValue(entity, field.getName());

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

    private Object getFieldValue(ENTITY entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            return entity.getClass().getMethod(methodName).invoke(entity);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldValue(Object entity, String fieldName, ENTITY value) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            entity.getClass().getMethod(methodName, field.getType()).invoke(entity, value);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
