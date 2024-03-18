package br.com.desafio.totvs.desafiobackend.service;

import br.com.desafio.totvs.desafiobackend.enums.ClienteRegraNegocio;
import br.com.desafio.totvs.desafiobackend.enums.GenericMessageError;
import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import br.com.desafio.totvs.desafiobackend.model.Cliente;
import br.com.desafio.totvs.desafiobackend.model.TelefoneCliente;
import br.com.desafio.totvs.desafiobackend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    ClienteService clienteService;

    @MockBean
    ClienteRepository clienteRepository;

    @MockBean
    TelefoneClienteService telefoneClienteService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void deveIncluirClienteSemTelefoneComSucesso() {
        Cliente clienteSemTelefone = new Cliente(1L, "Cliente Sem Telefone", "Rua 1", "Bairro 1", null);
        when(clienteRepository.save(clienteSemTelefone)).thenReturn(clienteSemTelefone);

        Cliente clienteIncluido = clienteService.incluir(clienteSemTelefone);

        assertEquals(clienteIncluido, clienteSemTelefone);
        verify(clienteRepository).save(clienteSemTelefone);
    }

    @Test
    void deveRetirarOsEspacosEmBrancoDosCamposAoIncluir() {
        Cliente clienteComEspacos = new Cliente(1L, " Cliente    Com    Espaço   ", "  Rua 1   ", "  Bairro   1   ",
                Collections.singletonList(new TelefoneCliente(1L, "  62999999999  ", null)));

        Cliente clienteSemEspacos = new Cliente(1L, "Cliente Com Espaço", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "62999999999", null)));

        when(clienteRepository.save(clienteComEspacos)).thenReturn(clienteComEspacos);

        Cliente clienteIncluido = clienteService.incluir(clienteComEspacos);

        assertEquals(clienteIncluido, clienteSemEspacos);
        verify(clienteRepository).save(clienteSemEspacos);
    }

    @Test
    void deveVerificarSeONomeDoClienteFoiInformadoAoIncluir() {
        Cliente cliente = new Cliente(1L, "  ", "Rua 1", "Bairro 1", null);

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.incluir(cliente));

        assertEquals(ClienteRegraNegocio.NOME_OBRIGATORIO.getMessage(), e.getMessage());
        verifyNoInteractions(clienteRepository);
    }

    @Test
    void deveVerificarSeONomeDoClienteTemNoMinimoDezCaracteresAoIncluir() {
        Cliente cliente = new Cliente(1L, "Cliente", "Rua 1", "Bairro 1", null);

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.incluir(cliente));

        assertEquals(ClienteRegraNegocio.NOME_MINIMO_CARACTERES.getMessage(), e.getMessage());
        verifyNoInteractions(clienteRepository);
    }

    @Test
    void deveVerificarSeOClienteJaFoiIncluido() {
        Cliente cliente = new Cliente(1L, "cliente ja incluso", "Rua 1", "Bairro 1", null);
        Cliente clienteIncluido = new Cliente(2L, "CLIENTE JA INCLUSO", "Rua 2", "Bairro 2", null);

        when(clienteRepository.findByNomeContainingIgnoreCase(cliente.getNome())).thenReturn(Optional.of(Collections.singletonList(clienteIncluido)));

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.incluir(cliente));

        assertEquals(ClienteRegraNegocio.CLIENTE_NOME_EXISTENTE.getMessage(), e.getMessage());
        verify(clienteRepository).findByNomeContainingIgnoreCase(cliente.getNome());
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveIncluirClienteComTelefoneComSucesso() {
        Cliente clienteComTelefone = new Cliente(1L, "Cliente Com Telefone", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "62999999999", null)));

        when(clienteRepository.save(clienteComTelefone)).thenReturn(clienteComTelefone);

        Cliente clienteIncluido = clienteService.incluir(clienteComTelefone);

        assertEquals(clienteIncluido, clienteComTelefone);
        verify(clienteRepository).save(clienteComTelefone);
    }

    @Test
    void deveVerificarSeOTelefoneDoClienteFoiInformadoAoIncluir() {
        Cliente cliente = new Cliente(1L, "Cliente Sem Telefone", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "  ", null)));

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.incluir(cliente));

        assertEquals(ClienteRegraNegocio.TELEFONE_OBRIGATORIO.getMessage(), e.getMessage());
        verifyNoInteractions(clienteRepository);
    }

    @Test
    void deveVerificarSeOTelefoneDoClienteEValidoAoIncluir() {
        Cliente cliente = new Cliente(1L, "Cliente Sem Telefone", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "123", null)));

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.incluir(cliente));

        assertEquals(ClienteRegraNegocio.TELEFONE_INVALIDO.getMessage(), e.getMessage());
        verifyNoInteractions(clienteRepository);
    }

    @Test
    void deveVerificarSeOTelefoneDoClienteJaFoiIncluido() {
        Cliente cliente = new Cliente(1L, "Cliente Sem Telefone", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "62999999999", new Cliente(1L))));
        TelefoneCliente telefoneClienteIncluido = new TelefoneCliente(2L, "62999999999", new Cliente(2L));

        when(telefoneClienteService.findByTelefone(telefoneClienteIncluido.getTelefone()))
                .thenReturn(Optional.of(Collections.singletonList(telefoneClienteIncluido)));

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.incluir(cliente));

        assertEquals(ClienteRegraNegocio.TELEFONE_EXISTENTE.getMessage(), e.getMessage());
        verify(telefoneClienteService).findByTelefone(telefoneClienteIncluido.getTelefone());
        verifyNoMoreInteractions(telefoneClienteService);
    }

    @Test
    void deveVerificarSeONomeDoClienteJaFoiIncluidoAoAlterar() {
        Cliente cliente = new Cliente(1L, "Cliente ja incluso", "Rua 1", "Bairro 1", null);
        Cliente clienteIncluido = new Cliente(2L, "CLIENTE JA INCLUSO", "Rua 2", "Bairro 2", null);

        when(clienteRepository.findByNomeContainingIgnoreCase(cliente.getNome()))
                .thenReturn(Optional.of(Collections.singletonList(clienteIncluido)));

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.alterar(cliente));

        assertEquals(ClienteRegraNegocio.CLIENTE_NOME_EXISTENTE.getMessage(), e.getMessage());
        verify(clienteRepository).findByNomeContainingIgnoreCase(cliente.getNome());
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveVerificarSeOTelefoneDoClienteJaFoiIncluidoAoAlterar() {
        Cliente cliente = new Cliente(1L, "Cliente Sem Telefone", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "62999999999", new Cliente(1L))));
        TelefoneCliente telefoneClienteIncluido = new TelefoneCliente(2L, "62999999999", new Cliente(2L));

        when(telefoneClienteService.findByTelefone(cliente.getTelefones().get(0).getTelefone()))
                .thenReturn(Optional.of(Collections.singletonList(telefoneClienteIncluido)));

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.alterar(cliente));

        assertEquals(ClienteRegraNegocio.TELEFONE_EXISTENTE.getMessage(), e.getMessage());
        verify(telefoneClienteService).findByTelefone(telefoneClienteIncluido.getTelefone());
        verifyNoMoreInteractions(telefoneClienteService);
    }

    @Test
    void deveVerificarSeOTelefoneDoClienteEValidoAoAlterar() {
        Cliente cliente = new Cliente(1L, "Cliente Sem Telefone", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "123", new Cliente(1L))));

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.alterar(cliente));

        assertEquals(ClienteRegraNegocio.TELEFONE_INVALIDO.getMessage(), e.getMessage());
        verifyNoInteractions(clienteRepository);
    }

    @Test
    void deveVerificarSeOTelefoneDoClienteFoiInformadoAoAlterar() {
        Cliente cliente = new Cliente(1L, "Cliente Sem Telefone", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "  ", new Cliente(1L))));

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.alterar(cliente));

        assertEquals(ClienteRegraNegocio.TELEFONE_OBRIGATORIO.getMessage(), e.getMessage());
        verifyNoInteractions(clienteRepository);
    }

    @Test
    void deveVerificarSeONomeDoClienteFoiInformadoAoAlterar() {
        Cliente cliente = new Cliente(1L, "  ", "Rua 1", "Bairro 1", null);

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.alterar(cliente));

        assertEquals(ClienteRegraNegocio.NOME_OBRIGATORIO.getMessage(), e.getMessage());
        verifyNoInteractions(clienteRepository);
    }

    @Test
    void deveRetirarOsEspacosEmBrancoDosCamposAoAlterar() {
        Cliente clienteComEspacos = new Cliente(1L, " Cliente    Com    Espaço   ", "  Rua 1   ", "  Bairro   1   ",
                Collections.singletonList(new TelefoneCliente(1L, "  62999999999  ", null)));

        Cliente clienteSemEspacos = new Cliente(1L, "Cliente Com Espaço", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "62999999999", null)));

        when(clienteRepository.save(clienteComEspacos)).thenReturn(clienteComEspacos);
        when(clienteRepository.existsById(clienteComEspacos.getId())).thenReturn(true);

        Cliente clienteIncluido = clienteService.alterar(clienteComEspacos);

        assertEquals(clienteIncluido, clienteSemEspacos);
        verify(clienteRepository).save(clienteSemEspacos);
    }

    @Test
    void deveAlterarClienteComTelefone() {
        Cliente clienteComTelefone = new Cliente(1L, "Cliente Com Telefone", "Rua 1", "Bairro 1",
                Collections.singletonList(new TelefoneCliente(1L, "62999999999", new Cliente(1L))));

        when(clienteRepository.existsById(clienteComTelefone.getId())).thenReturn(true);
        when(clienteRepository.save(clienteComTelefone)).thenReturn(clienteComTelefone);

        Cliente clienteIncluido = clienteService.alterar(clienteComTelefone);

        assertEquals(clienteIncluido, clienteComTelefone);
        verify(clienteRepository).save(clienteComTelefone);
    }

    @Test
    void deveExcluirClienteComSucesso() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);

        clienteService.excluir(id);

        verify(clienteRepository).deleteById(id);
    }

    @Test
    void deveVerificarSeOClienteExisteAoExcluir() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(false);

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.excluir(id));

        assertEquals(GenericMessageError.REGISTRO_NAO_ENCONTRADO.getMessage(), e.getMessage());
        verify(clienteRepository).existsById(id);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveObterClientePorIdComSucesso() {
        Long id = 1L;
        Cliente cliente = new Cliente(id, "Cliente", "Rua 1", "Bairro 1", null);
        when(clienteRepository.findById(id)).thenReturn(java.util.Optional.of(cliente));

        Cliente clienteObtido = clienteService.obterPorId(id);

        assertEquals(clienteObtido, cliente);
        verify(clienteRepository).findById(id);
    }

    @Test
    void deveVerificarSeOIdDoClienteFoiInformadoAoObterPorId() {
        Long id = null;

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.obterPorId(id));

        assertEquals(GenericMessageError.REGISTRO_NAO_INFORMADO.getMessage(), e.getMessage());
        verifyNoInteractions(clienteRepository);
    }

    @Test
    void deveRetornarExceptionAoObterClientePorIdInexistente() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(java.util.Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> clienteService.obterPorId(id));

        assertEquals(GenericMessageError.REGISTRO_NAO_ENCONTRADO.getMessage(), e.getMessage());
        verify(clienteRepository).findById(id);
    }

    @Test
    void deveObterTodosOsClientesComSucesso() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        clienteService.obterTodos();

        verify(clienteRepository).findAll();
    }
}
