package br.ufrn.imd.bd.model;

public class Caixa extends Funcionario {

    public Caixa(Funcionario funcionario) {
        super();
        if(funcionario != null) {
            this.setId(funcionario.getId());
            this.setNome(funcionario.getNome());
            this.setLogin(funcionario.getLogin());
            this.setSenha(funcionario.getSenha());
            this.setEmail(funcionario.getEmail());
            this.setDataCadastro(funcionario.getDataCadastro());
            this.setAtivo(funcionario.getAtivo());
        }
    }

    public Caixa() {}
}
