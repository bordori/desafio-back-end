package br.com.desafio.totvs.desafiobackend.controller;


import br.com.desafio.totvs.desafiobackend.dto.ClienteDto;
import br.com.desafio.totvs.desafiobackend.mapper.ClienteMapper;
import br.com.desafio.totvs.desafiobackend.mapper.EntityMapper;
import br.com.desafio.totvs.desafiobackend.model.Cliente;
import br.com.desafio.totvs.desafiobackend.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cliente")
public class ClienteController extends AbstractController<Cliente, ClienteDto, Long, ClienteService, ClienteMapper> {


}
