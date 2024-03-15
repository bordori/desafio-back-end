package br.com.desafio.totvs.desafiobackend.dto;

import br.com.desafio.totvs.desafiobackend.validation.constraints.Telefone;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TelefoneClienteDto {

    private Long id;

    @NotBlank(message = "Telefone é obrigatório")
    @Telefone
    private String telefone;
}
