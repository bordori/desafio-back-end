package br.com.desafio.totvs.desafiobackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClienteDto {

    private String nome;

    private String endereco;

    private String bairro;

    private List<TelefoneClienteDto> telefones;

}
