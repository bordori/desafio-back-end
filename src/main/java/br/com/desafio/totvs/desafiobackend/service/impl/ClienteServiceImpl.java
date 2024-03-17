package br.com.desafio.totvs.desafiobackend.service.impl;

import br.com.desafio.totvs.desafiobackend.enums.ClienteRegraNegocio;
import br.com.desafio.totvs.desafiobackend.exception.BusinessException;
import br.com.desafio.totvs.desafiobackend.model.Cliente;
import br.com.desafio.totvs.desafiobackend.model.IEntity;
import br.com.desafio.totvs.desafiobackend.model.TelefoneCliente;
import br.com.desafio.totvs.desafiobackend.repository.ClienteRepository;
import br.com.desafio.totvs.desafiobackend.service.ClienteService;
import br.com.desafio.totvs.desafiobackend.service.TelefoneClienteService;
import br.com.desafio.totvs.desafiobackend.util.Util;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço de cliente
 * para validações de campos e regras de negócio
 * implementando {@link ClienteService}
 * e extendendo {@link BaseServiceImpl}
 */
@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, Long, ClienteRepository> implements ClienteService {

    @Autowired
    private TelefoneClienteService telefoneClienteService;

    /**
     * Método implementado de {@link BaseServiceImpl#validarCampos(IEntity)}
     * para validar campos obrigatórios
     * @param cliente entidade {@link Cliente}
     * @throws BusinessException caso ocorra erro de validação
     */
    @Override
    public void validarCampos(Cliente cliente)  {
        if (Strings.isEmpty(cliente.getNome()) || cliente.getNome().isBlank()) {
            throw new BusinessException(ClienteRegraNegocio.NOME_OBRIGATORIO);
        }

        if (cliente.getNome().length() < 10) {
            throw new BusinessException(ClienteRegraNegocio.NOME_MINIMO_CARACTERES);
        }

        verificaSeExisteTelefoneDuplicado(cliente);

        if (cliente.getTelefones() != null) {
            for (TelefoneCliente telefone : cliente.getTelefones()) {
                if (Strings.isEmpty(telefone.getTelefone()) || telefone.getTelefone().isBlank()) {
                    throw new BusinessException(ClienteRegraNegocio.TELEFONE_OBRIGATORIO);
                } else if (!Util.validarTelefone(telefone.getTelefone())) {
                    throw new BusinessException(ClienteRegraNegocio.TELEFONE_INVALIDO);
                }
            }
        }
    }

    /**
     * Método para verificar se existe telefone duplicado
     * na lista de telefones do cliente
     * @param cliente entidade {@link Cliente}
     * @throws BusinessException caso ocorra erro de validação
     */
    private void verificaSeExisteTelefoneDuplicado(Cliente cliente) {
        if (cliente.getTelefones() == null) return;
        List<TelefoneCliente> telefones = cliente.getTelefones();
        for (int i = 0; i < telefones.size(); i++) {
            for (int j = i + 1; j < telefones.size(); j++) {
                if (telefones.get(i).getTelefone().equals(telefones.get(j).getTelefone())) {
                    throw new BusinessException(ClienteRegraNegocio.TELEFONE_DUPLICADO);
                }
            }
        }
    }

    /**
     * Método implementado de {@link BaseServiceImpl#validarRegrasNegocio(IEntity)}
     * para validar regras de negócio
     * @param entity entidade {@link Cliente}
     * @throws BusinessException caso ocorra erro de validação
     */
    @Override
    public void validarRegrasNegocio(Cliente entity) {
        List<Cliente> clientes = obterPorNome(entity.getNome());
        if (clientes != null && !clientes.isEmpty()) {
            if (clientes.size() > 1 || !clientes.get(0).getId().equals(entity.getId())) {
                throw new BusinessException(ClienteRegraNegocio.CLIENTE_NOME_EXISTENTE);
            }
        }

        if (entity.getTelefones() != null)
            for (TelefoneCliente telefone : entity.getTelefones()) {
                List<TelefoneCliente> clientesTelefone = telefoneClienteService.findByTelefone(telefone.getTelefone());
                if (clientesTelefone != null && !clientesTelefone.isEmpty()) {
                    for (TelefoneCliente telefoneCliente : clientesTelefone) {
                        if (telefoneCliente.getCliente().getId().equals(telefone.getCliente().getId())){
                            telefone.setId(telefoneCliente.getId());
                        } else {
                            throw new BusinessException(ClienteRegraNegocio.TELEFONE_EXISTENTE);
                        }
                    }
                }
            }
    }

    /**
     * Método implementado de {@link BaseServiceImpl#prepararDadosIncluirAlterar(IEntity)}
     * para preparar os dados antes de incluir ou alterar
     * @param cliente entidade {@link Cliente}
     */
    @Override
    public void prepararDadosIncluirAlterar(Cliente cliente) {
        if (cliente.getTelefones() != null)
            for (TelefoneCliente telefone : cliente.getTelefones()) {
                telefone.setTelefone(Util.somenteNumeros(telefone.getTelefone()));
            }
        super.prepararDadosIncluirAlterar(cliente);
    }

    /**
     * Método para obter cliente por nome
     * @param nome nome do cliente
     * @return lista de clientes {@link Cliente}
     */
    @Override
    public List<Cliente> obterPorNome(String nome) {
        return repository.findByNome(nome);
    }
}
