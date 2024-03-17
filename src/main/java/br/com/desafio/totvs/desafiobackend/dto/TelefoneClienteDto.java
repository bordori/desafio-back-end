package br.com.desafio.totvs.desafiobackend.dto;

import br.com.desafio.totvs.desafiobackend.validation.constraints.Telefone;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Classe para representação de telefone do cliente
 * para operações de api rest
 */
@Data
public class TelefoneClienteDto {

    private Long id;

    @NotBlank(message = "cliente.telefone.obrigatorio")
    @Telefone
    private String telefone;
}
