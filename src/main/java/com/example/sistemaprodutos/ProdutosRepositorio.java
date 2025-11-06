package com.example.sistemaprodutos;

import java.util.ArrayList;
import java.util.List;

public class ProdutosRepositorio {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();
    private static int nextUserId = 1;
    private static int nextProdutoId = 1;

    static {
        // Usuários pré-cadastrados
        // Funcionário: admin - 123
        Usuario admin = new Usuario(nextUserId++, "Administrador", "admin", "123", "funcionario");
        usuarios.add(admin);

        // Cliente: gabriel - 123
        Usuario cliente = new Usuario(nextUserId++, "Gabriel", "gabriel", "123", "cliente");
        usuarios.add(cliente);

        // Alguns produtos iniciais
        produtos.add(new Produto(nextProdutoId++, "Caneta", 2.50));
        produtos.add(new Produto(nextProdutoId++, "Caderno", 12.00));
        produtos.add(new Produto(nextProdutoId++, "Mochila", 80.00));
    }

    // Usuários
    public static synchronized Usuario buscarUsuarioPorLogin(String login) {
        for (Usuario u : usuarios) if (u.getLogin().equalsIgnoreCase(login)) return u;
        return null;
    }

    public static synchronized Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : usuarios) if (u.getId() == id) return u;
        return null;
    }

    public static synchronized boolean cadastrarUsuario(String nome, String login, String senha, String tipo) {
        if (buscarUsuarioPorLogin(login) != null) return false; // login duplicado
        Usuario u = new Usuario(nextUserId++, nome, login, senha, tipo);
        usuarios.add(u);
        return true;
    }

    public static synchronized boolean cadastrarFuncionario(String nome, String login, String senha) {
        return cadastrarUsuario(nome, login, senha, "funcionario");
    }

    // Produtos
    public static synchronized List<Produto> listarProdutos() { return new ArrayList<>(produtos); }

    public static synchronized Produto encontrarPorId(int id) {
        for (Produto p : produtos) if (p.getId() == id) return p;
        return null;
    }

    public static synchronized Produto buscarProduto(int id) {
        return encontrarPorId(id);
    }

    // agora retorna boolean: true se inseriu com sucesso
    public static synchronized boolean adicionarProduto(String nome, double preco) {
        Produto p = new Produto(nextProdutoId++, nome, preco);
        return produtos.add(p);
    }

    public static synchronized boolean removerProduto(int id) {
        return produtos.removeIf(p -> p.getId() == id);
    }

    // retorna true se editou
    public static synchronized boolean editar(int id, String novoNome, double novoPreco) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                p.setNome(novoNome);
                p.setPreco(novoPreco);
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean editarProduto(int id, String nome, double preco) {
        return editar(id, nome, preco);
    }
}