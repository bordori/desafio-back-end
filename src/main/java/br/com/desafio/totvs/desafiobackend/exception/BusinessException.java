package br.com.desafio.totvs.desafiobackend.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.Map;

@Getter
public class BusinessException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private AbstractMessageCodeError code;
    private final Map<String, Object> mapDetails;

    public BusinessException(AbstractMessageCodeError code) {
        this.code = code;
        this.mapDetails = Map.of();
    }

    public BusinessException(final Throwable ex) {
        super(ex);
        this.mapDetails = null;
    }

    public BusinessException() {
        this.mapDetails = null;
    }

}
