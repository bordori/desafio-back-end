package br.com.desafio.totvs.desafiobackend.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDto {
    private Integer status;
    private String code;
    private String error;
    private Set<ErrorDto> errors;

    public ApiErrorDto() {
    }

    public ApiErrorDto(Integer status, String code, String error) {
        this.status = status;
        this.code = code;
        this.error = error;
    }

    public ApiErrorDto(Integer status, String code, Set<ErrorDto> errors) {
        this.status = status;
        this.code = code;
        this.errors = errors;
    }
}
