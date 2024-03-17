package br.com.desafio.totvs.desafiobackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * Classe para representação de cliente
 * para operações de api rest
 */
@Data
public class ClienteDto {

    private Long id;

    @NotBlank(message = "{cliente.nome.obrigatorio}")
    @Length(min = 10, message = "{cliente.nome.min.tamanho}")
    private String nome;

    private String endereco;

    private String bairro;

    private List<@Valid  TelefoneClienteDto> telefones;

}
