package br.com.desafio.totvs.desafiobackend.config;

import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe para tratamento de exceções na API
 */
@ControllerAdvice
public class ApiRestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Método para tratamento de exceções de negócio da api
     * @param ex exceção de negócio {@link BusinessException}
     * @return {@link ResponseEntity<Object>}
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        if (ex.getCode() == null){
            return ResponseEntity.status(500).body(ex.getMessage());
        } else {
            HttpStatus httpStatus = HttpStatus.valueOf(ex.getCode());
            return ResponseEntity
                    .status(httpStatus)
                    .body(new ApiErrorDto(httpStatus.value(), httpStatus.getReasonPhrase(),ex.getMessage()));
        }
    }

    /**
     * Método para tratamento de exceções de validação de campos
     * @param ex exceção de validação de campos {@link MethodArgumentNotValidException}
     * @param headers cabeçalhos da requisição
     * @param status status da requisição
     * @param request requisição
     * @return {@link ResponseEntity<Object>}
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Set<ErrorDto> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorDto(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toSet());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), errors));
    }
}
