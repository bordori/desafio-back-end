package br.com.desafio.totvs.desafiobackend.enums;

import br.com.desafio.totvs.desafiobackend.exception.AbstractMessageCodeError;
import lombok.Getter;

/**
 * Enum para regras de negócio de cliente
 * com código e mensagem de erro
 * implements {@link AbstractMessageCodeError}
 */
public enum ClienteRegraNegocio implements AbstractMessageCodeError {

    NOME_OBRIGATORIO(400, "O nome do cliente é obrigatório"),
    NOME_MINIMO_CARACTERES(400, "O nome do cliente deve ter no mínimo 10 caracteres"),
    CLIENTE_NOME_EXISTENTE(409, "Já existe um cliente com este nome"),
    TELEFONE_INVALIDO(400, "Telefone inválido"),
    TELEFONE_OBRIGATORIO(400, "O telefone do cliente é obrigatório"),
    TELEFONE_EXISTENTE(409, "Já existe um cliente com este telefone"),
    TELEFONE_DUPLICADO(409, "Não é permitido incluir o mesmo telefone para o mesmo cliente");

    private final Integer code;
    private final String message;

    ClienteRegraNegocio(final Integer code, final String message) {
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
