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

@ControllerAdvice
public class ApiRestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    ApiRestResponseExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {

        if (ex.getCode() == null){
            return ResponseEntity.status(500).body(ex.getMessage());
        } else {
            HttpStatus httpStatus = HttpStatus.valueOf(ex.getCode().getCode());
            return ResponseEntity
                    .status(httpStatus)
                    .body(new ApiErrorDto(httpStatus.value(), httpStatus.getReasonPhrase(), getMessage(ex)));
        }
    }

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

    private String getMessage(BusinessException ex) {
        return messageSource.getMessage(ex.getCode().getMessage(), null, Locale.getDefault());
    }
}
