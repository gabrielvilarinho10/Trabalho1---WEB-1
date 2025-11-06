package com.example.sistemaprodutos.servlets;

import com.example.sistemaprodutos.ProdutosRepositorio;
import com.example.sistemaprodutos.Usuario;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/adicionarproduto")
public class AdicionarProduto extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession sessao = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>➕ Adicionar Produto</h1>");

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado.</p>");
            out.println("<a href='index.html'>Fazer Login</a>");
            out.println("</body></html>");
            return;
        }

        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        if (!u.isFuncionario()) {
            out.println("<p>❌ Apenas funcionários podem adicionar produtos.</p>");
            out.println("<a href='menu.html'>Voltar ao Menu</a>");
            out.println("</body></html>");
            return;
        }

        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");
        double preco = 0.0;

        try {
            preco = Double.parseDouble(precoStr);
        } catch (Exception e) {
            out.println("<p>❌ Preço inválido.</p>");
            out.println("<a href='produtos'>← Voltar</a>");
            out.println("</body></html>");
            return;
        }

        boolean ok = ProdutosRepositorio.adicionarProduto(nome, preco);

        if (ok) {
            out.println("<p>✅ <strong>Produto adicionado com sucesso!</strong></p>");
            out.println("<p>📦 Nome: " + nome + "</p>");
            out.println("<p>💰 Preço: R$ " + String.format("%.2f", preco) + "</p>");
        } else {
            out.println("<p>❌ Não foi possível adicionar o produto.</p>");
        }

        out.println("<hr>");
        out.println("<p><a href='produtos'>← Ver Produtos</a></p>");
        out.println("</body></html>");
    }
}