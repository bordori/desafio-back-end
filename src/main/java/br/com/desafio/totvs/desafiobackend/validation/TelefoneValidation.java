package br.com.desafio.totvs.desafiobackend.validation;

import br.com.desafio.totvs.desafiobackend.util.Util;
import br.com.desafio.totvs.desafiobackend.validation.constraints.Telefone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe para validação de telefone
 * {@link ConstraintValidator}
 */
public class TelefoneValidation implements ConstraintValidator<Telefone, String> {

    /**
     * Método para validar telefone
     * @param value {@link String}
     * @param constraintValidatorContext {@link ConstraintValidatorContext}
     * @return {@link Boolean}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String telefone = value != null ? value : "";

        return Util.validarTelefone(telefone);
    }
}
