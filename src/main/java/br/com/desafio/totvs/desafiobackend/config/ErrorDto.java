package br.com.desafio.totvs.desafiobackend.config;

import lombok.Getter;
import lombok.Setter;

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
