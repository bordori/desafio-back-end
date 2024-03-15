package br.com.desafio.totvs.desafiobackend.enums;

import br.com.desafio.totvs.desafiobackend.exception.AbstractMessageCodeError;
import lombok.Getter;

public enum ClienteRegraNegocioEnum implements AbstractMessageCodeError {

    NOME_OBRIGATORIO(400, "cliente.nome.obrigatorio"),
    NOME_MINIMO_CARACTERES(400, "cliente.nome.min.tamanho"),
    CLIENTE_NOME_EXISTENTE(409, "cliente.nome.existente");

    private final Integer code;
    private final String message;

    ClienteRegraNegocioEnum(final Integer code, final String message) {
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
