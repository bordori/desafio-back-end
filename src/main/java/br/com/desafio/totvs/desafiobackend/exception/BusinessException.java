package br.com.desafio.totvs.desafiobackend.exception;

import lombok.Getter;

/**
 * Classe para representação de exceções de negócio
 * extends {@link RuntimeException}
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;


    public BusinessException(AbstractMessageCodeError code) {
        super(code.getMessage());
        this.code = code.getCode();
    }
}
