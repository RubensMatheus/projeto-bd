package br.ufrn.imd.bd.dao;


import br.ufrn.imd.bd.model.Atendente;
import br.ufrn.imd.bd.model.Funcionario;
import br.ufrn.imd.bd.model.TipoAtendente;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AtendenteDAO extends FuncionarioDAO {

    @Override
    protected Funcionario mapResult(ResultSet rs) throws SQLException {
        Atendente atendente = new Atendente();
        atendente.setTipo(TipoAtendente.valueOf(rs.getString("tipo")));
        return super.mapResult(rs);
    }

    @Override
    public String getTableName() {
        return "atendentes";
    }
}
