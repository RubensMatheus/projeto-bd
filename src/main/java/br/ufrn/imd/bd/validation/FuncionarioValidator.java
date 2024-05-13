package br.ufrn.imd.bd.validation;

import br.ufrn.imd.bd.dao.FuncionarioDAO;
import br.ufrn.imd.bd.exceptions.FuncionarioJaExisteException;
import br.ufrn.imd.bd.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class FuncionarioValidator {

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    public void validar(Connection conn, Funcionario funcionario) throws SQLException, FuncionarioJaExisteException {

        if(funcionarioDAO.existeFuncionarioComParametroEId(conn, "email", funcionario.getEmail(), funcionario.getId())) {
            throw new FuncionarioJaExisteException("Já existe um funcionário com esse email!");
        }

        if(funcionarioDAO.existeFuncionarioComParametroEId(conn, "login", funcionario.getLogin(), funcionario.getId())) {
            throw new FuncionarioJaExisteException("Já existe um funcionário com esse login!");
        }
    }
}