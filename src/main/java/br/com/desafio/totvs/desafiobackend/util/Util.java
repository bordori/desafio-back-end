package br.com.desafio.totvs.desafiobackend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilitária com métodos estáticos
 * para manipulação e validação de dados
 */
public class Util {

    /**
     * Método para retirar todos os caracteres não numéricos
     * de uma {@link String}
     * @param texto {@link String}
     * @return {@link String}
     */
    public static String somenteNumeros(String texto) {
        return texto.replaceAll("[^0-9]", "");
    }

    /**
     * Método para retirar espaços vazios de uma {@link String}
     * @param texto {@link String}
     * @return {@link String}
     */
    public static String retiraEspacosVazios(String texto) {
        return texto.trim().replaceAll(" +", " ");
    }

    /**
     * Método para validar se um telefone é válido
     * @param telefone {@link String}
     * @return {@link Boolean}
     */
    public static boolean validarTelefone(String telefone) {
        Pattern pattern = Pattern.compile("\\(?\\d{2}\\)?[-\\s]?\\d{8,9}");
        return pattern.matcher(telefone).matches();
    }
}
