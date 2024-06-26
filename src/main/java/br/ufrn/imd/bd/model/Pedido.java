package br.ufrn.imd.bd.model;

import br.ufrn.imd.bd.model.enums.ProgressoPedido;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private Long id;

    private Atendente atendente;

    private Conta conta;


    @NotNull(message = "Status é obrigatório.")
    private ProgressoPedido progressoPedido;

    private LocalDateTime dataRegistro;

    private List<PedidoInstancia> produtos;

    public Pedido() {
        atendente = new Atendente();
        conta = new Conta();
        produtos = new ArrayList<>();
    }

    public List<PedidoInstancia> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<PedidoInstancia> produtos) {
        this.produtos = produtos;
    }

    public void addProduto(PedidoInstancia pedidoInstancia) {
        produtos.add(pedidoInstancia);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public ProgressoPedido getProgressoPedido() {
        return progressoPedido;
    }

    public void setProgressoPedido(ProgressoPedido progressoPedido) {
        this.progressoPedido = progressoPedido;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", atendente=" + atendente +
                ", conta=" + conta +
                ", progressoPedido=" + progressoPedido +
                ", dataRegistro=" + dataRegistro +
                ", produtos=" + produtos +
                '}';
    }

    public boolean containsProduto(Long produtoId) {
        return produtos.stream().anyMatch(pi -> pi.getInstanciaProduto().getProduto().getId().equals(produtoId));
    }
}
