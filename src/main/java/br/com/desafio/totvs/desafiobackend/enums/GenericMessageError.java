package br.com.desafio.totvs.desafiobackend.enums;

import br.com.desafio.totvs.desafiobackend.exception.AbstractMessageCodeError;

public enum GenericMessageError implements AbstractMessageCodeError {

    REGISTRO_NAO_ENCONTRADO(404, "registro.nao.encontrado"),
    REGISTRO_NAO_INFORMADO(400, "registro.nao.informado");


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
