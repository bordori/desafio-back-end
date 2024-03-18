package br.com.desafio.totvs.desafiobackend.controller;


import br.com.desafio.totvs.desafiobackend.dto.ClienteDto;
import br.com.desafio.totvs.desafiobackend.mapper.ClienteMapper;
import br.com.desafio.totvs.desafiobackend.model.Cliente;
import br.com.desafio.totvs.desafiobackend.service.ClienteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe para operações de api rest de cliente
 * extends {@link AbstractController}
 */
@RestController
@RequestMapping("api/cliente")
public class ClienteController extends AbstractController<Cliente, ClienteDto, Long, ClienteService, ClienteMapper> {


}
