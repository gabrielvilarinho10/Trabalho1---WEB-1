package com.example.sistemaprodutos.servlets;

import com.example.sistemaprodutos.Produto;
import com.example.sistemaprodutos.ProdutosRepositorio;
import com.example.sistemaprodutos.Usuario;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/produtos")
public class Produtos extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sessao = request.getSession(false);
        Usuario u = null;
        if (sessao != null) u = (Usuario) sessao.getAttribute("usuarioLogado");

        out.println("<html><body>");
        out.println("<h1>🛍️ Produtos Disponíveis na Loja</h1>");

        // Mostra informações do usuário se logado
        if (u != null) {
            out.println("<p>👤 Logado como: <strong>" + u.getNome() + "</strong> (" + u.getTipo() + ")</p>");
        } else {
            out.println("<p>👋 Visitante | <a href='index.html'>Fazer Login</a> para comprar</p>");
        }

        out.println("<hr>");

        List<Produto> lista = ProdutosRepositorio.listarProdutos();

        if (lista.isEmpty()) {
            out.println("<p>Nenhum produto disponível no momento.</p>");
        } else {
            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr style='background-color: #f0f0f0;'>");
            out.println("<th>ID</th><th>Produto</th><th>Preço</th><th>Ações</th>");
            out.println("</tr>");

            for (Produto p : lista) {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td><strong>" + p.getNome() + "</strong></td>");
                out.println("<td style='color: green;'><strong>R$ " + String.format("%.2f", p.getPreco()) + "</strong></td>");
                out.println("<td>");

                if (u != null && u.isCliente()) {
                    out.println("<a href='adicionarcarrinho?id=" + p.getId() + "'>🛒 Adicionar ao Carrinho</a>");
                } else if (u != null && u.isFuncionario()) {
                    out.println("<a href='editarproduto?id=" + p.getId() + "'>✏️ Editar</a>");
                    out.println(" | <a href='removerproduto?id=" + p.getId() + "'>🗑️ Remover</a>");
                } else {
                    out.println("Faça login para comprar");
                }

                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<p><small>Total de produtos: " + lista.size() + "</small></p>");
        }

        // Formulário de adicionar produto (só para funcionários)
        if (u != null && u.isFuncionario()) {
            out.println("<hr>");
            out.println("<h3>➕ Adicionar Novo Produto (Funcionário)</h3>");
            out.println("<form method='post' action='adicionarproduto'>");
            out.println("Nome: <input name='nome' required/> ");
            out.println("Preço: <input name='preco' type='number' step='0.01' required/> ");
            out.println("<button type='submit'>Adicionar</button>");
            out.println("</form>");
        }

        out.println("<hr>");
        out.println("<p>");
        out.println("<a href='vercarrinho'>🛒 Ver Meu Carrinho</a> | ");
        out.println("<a href='menu.html'>← Voltar ao Menu</a>");
        out.println("</p>");
        out.println("</body></html>");
    }
}