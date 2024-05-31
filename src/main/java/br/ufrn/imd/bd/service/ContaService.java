package br.ufrn.imd.bd.service;

import br.ufrn.imd.bd.connection.DatabaseConfig;
import br.ufrn.imd.bd.dao.ContaDAO;
import br.ufrn.imd.bd.exceptions.EntidadeJaExisteException;
import br.ufrn.imd.bd.model.Conta;
import br.ufrn.imd.bd.model.InstanciaProduto;
import br.ufrn.imd.bd.model.Mesa;
import br.ufrn.imd.bd.model.Telefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaDAO contaDAO;

    public List<Conta> buscarTodos() throws SQLException {
        return contaDAO.buscarTodos();
    }

    public Object buscarPorId(Long id) throws SQLException {
        return contaDAO.buscarPorId(id);
    }

    public void deletar(Long id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection()){
            Conta conta = contaDAO.buscarPorId(id);
            conta.setAtivo(false);
            contaDAO.atualizar(conn, conta);
        }
    }

    public Conta salvar(Conta conta) throws SQLException {

        try (Connection conn = DatabaseConfig.getConnection()){
            conta = contaDAO.salvar(conn, conta);
        }

        return conta;
    }

    public void atualizar(Conta conta) throws SQLException {

        if ("ABERTA".equals(conta.getStatusConta().name())) {
            conta.setMetodoPagamento(null);
        }

        try (Connection conn = DatabaseConfig.getConnection()){
            contaDAO.atualizar(conn,conta);
        }
    }
}