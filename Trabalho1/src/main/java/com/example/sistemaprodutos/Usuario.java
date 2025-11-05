package com.example.sistemaprodutos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nome;
    private String login;
    private String senha;
    private String tipo; // cliente ou funcionario
    private List<Produto> carrinho = new ArrayList<>();

    public Usuario(int id, String nome, String login, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<Produto> getCarrinho() { return carrinho; }

    public void adicionarAoCarrinho(Produto p) { carrinho.add(p); }

    // agora retorna boolean: true se removeu, false se não encontrou
    public boolean removerDoCarrinho(int produtoId) {
        return carrinho.removeIf(p -> p.getId() == produtoId);
    }

    // alias com nome diferente — compatibilidade
    public boolean removerDoCarrinhoPorId(int produtoId) {
        return removerDoCarrinho(produtoId);
    }

    public double valorTotalCarrinho() {
        double total = 0.0;
        for (Produto p : carrinho) total += p.getPreco();
        return total;
    }

    public boolean isFuncionario() { return "funcionario".equalsIgnoreCase(tipo); }
    public boolean isCliente() { return "cliente".equalsIgnoreCase(tipo); }

    @Override
    public String toString() {
        return "Usuario{id="+id+", nome='"+nome+"', login='"+login+"', tipo='"+tipo+"'}";
    }
}
