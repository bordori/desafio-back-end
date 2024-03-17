package br.com.desafio.totvs.desafiobackend.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe para retorno de erros na API
 */
@Getter
@Setter
public class ErrorDto {

    private String key;
    private String message;

    public ErrorDto() {
    }

    public ErrorDto(String key, String message) {
        this.key = key;
        this.message = message;
    }

}
