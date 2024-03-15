package br.com.desafio.totvs.desafiobackend.exception;

import lombok.Getter;

public interface AbstractMessageCodeError {

    Integer getCode();
    String getMessage();

}
