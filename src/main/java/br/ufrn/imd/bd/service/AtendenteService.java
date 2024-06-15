package br.ufrn.imd.bd.service;

import br.ufrn.imd.bd.connection.DatabaseConfig;
import br.ufrn.imd.bd.dao.AtendenteDAO;
import br.ufrn.imd.bd.exceptions.EntidadeJaExisteException;
import br.ufrn.imd.bd.model.Atendente;
import br.ufrn.imd.bd.model.enums.TipoAtendente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class AtendenteService {

    @Autowired
    private AtendenteDAO atendenteDAO;

    public List<Atendente> buscarPorTipo(TipoAtendente tipo) throws SQLException {
        return atendenteDAO.buscarPorTipo(tipo);
    }

    public Atendente buscarPorId(Long id) throws SQLException {
        return atendenteDAO.buscarPorChave(id);
    }

    public Atendente salvar(Atendente atendente) throws SQLException, EntidadeJaExisteException {
        Connection conn = null;

        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            try {
                atendente = atendenteDAO.salvar(conn, atendente);
                conn.commit();
                return atendente;
            } catch (SQLException e) {
                DatabaseConfig.rollback(conn);
                if (e.getErrorCode() == 1062) {
                    if(atendente.getTipo().equals(TipoAtendente.GARCOM)) throw new EntidadeJaExisteException("Já existe um garçom com esse login.");
                    else throw new EntidadeJaExisteException("Já existe um gerente com esse login.");
                } else {
                    throw e;
                }
            }
        } finally {
            DatabaseConfig.close(conn);
        }
    }

    public void atualizar(Atendente atendente) throws EntidadeJaExisteException, SQLException {

        buscarPorId(atendente.getId());

        Connection conn = null;

        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            try {
                atendenteDAO.atualizar(conn, atendente);
                conn.commit();
            } catch (SQLException e) {
                DatabaseConfig.rollback(conn);
                if (e.getErrorCode() == 1062) {
                    if(atendente.getTipo().equals(TipoAtendente.GARCOM)) throw new EntidadeJaExisteException("Já existe um garçom com esse login.");
                    else throw new EntidadeJaExisteException("Já existe um gerente com esse login.");
                } else {
                    throw e;
                }
            }
        } finally {
            DatabaseConfig.close(conn);
        }
    }

    public void deletar(Long id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection()){
            atendenteDAO.deletar(conn, id);
        }

    }
}