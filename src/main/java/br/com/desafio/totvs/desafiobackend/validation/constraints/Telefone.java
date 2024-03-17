package br.com.desafio.totvs.desafiobackend.validation.constraints;

import br.com.desafio.totvs.desafiobackend.validation.TelefoneValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotação para validação de telefone
 * {@link TelefoneValidation}
 */
@Documented
@Constraint(validatedBy = TelefoneValidation.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Telefone {

    String message() default "{cliente.telefone.invalido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
