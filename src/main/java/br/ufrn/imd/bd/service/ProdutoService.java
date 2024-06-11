package br.ufrn.imd.bd.service;

import br.ufrn.imd.bd.connection.DatabaseConfig;
import br.ufrn.imd.bd.dao.InstanciaProdutoDAO;
import br.ufrn.imd.bd.dao.ProdutoDAO;
import br.ufrn.imd.bd.exceptions.EntidadeJaExisteException;
import br.ufrn.imd.bd.exceptions.EntidadeNaoExisteException;
import br.ufrn.imd.bd.model.InstanciaProduto;
import br.ufrn.imd.bd.model.Produto;
import br.ufrn.imd.bd.validation.ProdutoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private InstanciaProdutoDAO instanciaProdutoDAO;

    @Autowired
    private ProdutoDAO produtoDAO;

    @Autowired
    private ProdutoValidator produtoValidator;

    public List<InstanciaProduto> buscarTodos() throws SQLException {
        return instanciaProdutoDAO.buscarTodos();
    }

    public InstanciaProduto buscarPorId(Long id) throws SQLException, EntidadeNaoExisteException {

        InstanciaProduto instanciaProduto = instanciaProdutoDAO.buscarPorChave(id);
        if(instanciaProduto == null) {
            throw new EntidadeNaoExisteException("Produto não encontrado");
        }
        return instanciaProduto;
    }

    public void deletar(Long id) throws SQLException, EntidadeNaoExisteException {

        buscarPorId(id);

        try (Connection conn = DatabaseConfig.getConnection()) {
            instanciaProdutoDAO.deletar(conn, id);
        }
    }

    public InstanciaProduto salvar(InstanciaProduto instanciaProduto) throws SQLException, EntidadeJaExisteException {
        Connection conn = null;

        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            try {
                produtoValidator.validar(conn, instanciaProduto);
            } catch (EntidadeJaExisteException e) {
                conn.commit();
                throw e;
            }

            instanciaProduto.setProduto(this.salvarProduto(conn, instanciaProduto.getProduto()));
            instanciaProduto = this.salvarInstanciaProduto(conn, instanciaProduto);
            conn.commit();
        } catch (SQLException e) {
            DatabaseConfig.rollback(conn);
            throw e;
        } finally {
            DatabaseConfig.close(conn);
        }

        return instanciaProduto;
    }

    private InstanciaProduto salvarInstanciaProduto(Connection conn, InstanciaProduto instanciaProduto) throws SQLException {
        if (instanciaProduto.getId() == null) {
            return instanciaProdutoDAO.salvar(conn, instanciaProduto);
        }

        InstanciaProduto antiga = instanciaProdutoDAO.buscarPorChave(instanciaProduto.getId());

        if (antiga.getValor().compareTo(instanciaProduto.getValor()) != 0) {
            instanciaProdutoDAO.deletar(conn, antiga.getId());
            instanciaProdutoDAO.salvar(conn, instanciaProduto);
        } else {
            instanciaProdutoDAO.atualizar(conn, instanciaProduto);
        }

        return instanciaProduto;
    }


    private Produto salvarProduto(Connection conn, Produto produto) throws SQLException, EntidadeJaExisteException {
        if (produto.getId() == null) {
            produto = produtoDAO.salvar(conn, produto);
        } else {
            produtoDAO.atualizar(conn, produto);
        }

        return produto;
    }
}