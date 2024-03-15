package br.com.desafio.totvs.desafiobackend.controller;


import br.com.desafio.totvs.desafiobackend.dto.ClienteDto;
import br.com.desafio.totvs.desafiobackend.mapper.ClienteMapper;
import br.com.desafio.totvs.desafiobackend.mapper.EntityMapper;
import br.com.desafio.totvs.desafiobackend.model.Cliente;
import br.com.desafio.totvs.desafiobackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @PostMapping("/incluir")
    ResponseEntity<ClienteDto> incluir(@RequestBody ClienteDto clienteDto) {
        Cliente cliente = clienteMapper.toEntity(clienteDto);
        cliente.getTelefones().forEach(telefone -> telefone.setCliente(cliente));
        return ResponseEntity.ok(clienteMapper.toDto(clienteService.incluir(cliente)));
    }
}
