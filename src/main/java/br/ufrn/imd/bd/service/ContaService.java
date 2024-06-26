package br.ufrn.imd.bd.service;

import br.ufrn.imd.bd.connection.DatabaseUtil;
import br.ufrn.imd.bd.dao.ContaDAO;
import br.ufrn.imd.bd.exceptions.EntidadeJaExisteException;
import br.ufrn.imd.bd.exceptions.EntidadeNaoExisteException;
import br.ufrn.imd.bd.model.Conta;

import br.ufrn.imd.bd.model.enums.StatusConta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ContaService {

    @Autowired
    private ContaDAO contaDAO;

    @Autowired
    private DatabaseUtil databaseUtil;

    public List<Conta> buscarTodos() throws SQLException {
        return contaDAO.buscarTodos();
    }

    public Conta buscarPorId(Long id) throws SQLException, EntidadeNaoExisteException {

        Conta conta = contaDAO.buscarPorChave(id);

        if(conta == null) {
            throw new EntidadeNaoExisteException("Conta não encontrada");
        }
        return conta;

    }

    public Conta buscarPorMesa(Long id) throws SQLException {

        Conta conta = contaDAO.buscarPorMesa(id);

        return conta;

    }

    public Conta salvar(Conta conta) throws SQLException {

        Connection conn = null;

        try {
            conn = databaseUtil.getConnection();
            conn.setAutoCommit(false);
            conta = contaDAO.salvar(conn, conta);
            conn.commit();
        } catch (SQLException e) {
            databaseUtil.rollback(conn);
            throw e;
        } finally {
            databaseUtil.close(conn);
        }

        return conta;
    }

    public void atualizar(Conta conta) throws SQLException, EntidadeNaoExisteException {
        Conta existente = buscarPorId(conta.getId());

        if(conta.getMesa() == null) {
            conta.setMesa(existente.getMesa());
        }

        if ("ABERTA".equals(conta.getStatusConta().name()) ||"SOLICITADA".equals(conta.getStatusConta().name())) {
            conta.setMetodoPagamento(null);
        }

        try (Connection conn = databaseUtil.getConnection()){
            contaDAO.atualizar(conn,conta);
        }
    }

    public Double obterTotal(Long id) throws SQLException {
        return contaDAO.obterTotalConta(id);
    }

    public Map<String, Double> getTotalGanhoPorMetodoPagamento() throws SQLException {
        return contaDAO.getTotalGanhoPorMetodoPagamento();
    }

}
