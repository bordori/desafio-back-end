package br.com.desafio.totvs.desafiobackend.enums;

import br.com.desafio.totvs.desafiobackend.exception.AbstractMessageCodeError;

/**
 * Enum para mensagens de erro genéricas
 */
public enum GenericMessageError implements AbstractMessageCodeError {

    REGISTRO_NAO_ENCONTRADO(404, "Registro não encontrado"),
    REGISTRO_NAO_INFORMADO(400, "Registro não informado"),
    REGISTRO_ERRO_SALVAR_EDITAR(409, "Erro ao salvar registro");


    private final Integer code;
    private final String message;

    GenericMessageError(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
