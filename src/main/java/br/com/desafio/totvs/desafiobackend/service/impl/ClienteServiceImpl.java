package br.com.desafio.totvs.desafiobackend.service.impl;

import br.com.desafio.totvs.desafiobackend.enums.ClienteRegraNegocioEnum;
import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import br.com.desafio.totvs.desafiobackend.model.Cliente;
import br.com.desafio.totvs.desafiobackend.repository.ClienteRepository;
import br.com.desafio.totvs.desafiobackend.service.ClienteService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, Long, ClienteRepository> implements ClienteService {

    @Override
    protected void validarCampos(Cliente cliente) throws BusinessException {
        if (Strings.isEmpty(cliente.getNome()) || cliente.getNome().isBlank()) {
            throw new BusinessException(ClienteRegraNegocioEnum.NOME_OBRIGATORIO);
        }

        if (cliente.getNome().length() < 10) {
            throw new BusinessException(ClienteRegraNegocioEnum.NOME_MINIMO_CARACTERES);
        }
    }

    @Override
    protected void validarDados(Cliente entity) throws BusinessException {
        List<Cliente> clientes = obterPorNome(entity.getNome());
        if (clientes != null && !clientes.isEmpty()) {
            if (clientes.size() > 1 || !clientes.get(0).getId().equals(entity.getId())) {
                throw new BusinessException(ClienteRegraNegocioEnum.CLIENTE_NOME_EXISTENTE);
            }
        }
    }


    @Override
    public List<Cliente> obterPorNome(String nome) {
        return repository.findByNome(nome);
    }
}
