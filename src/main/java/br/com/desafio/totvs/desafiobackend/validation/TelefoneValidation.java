package br.com.desafio.totvs.desafiobackend.validation;

import br.com.desafio.totvs.desafiobackend.validation.constraints.Telefone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelefoneValidation implements ConstraintValidator<Telefone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String telefone = value != null ? value : "";

        Pattern pattern = Pattern.compile("\\(?\\d{2}\\)?[-\\s]?\\d{8,9}");
        Matcher matcher = pattern.matcher(telefone);

        return matcher.matches();
    }
}
