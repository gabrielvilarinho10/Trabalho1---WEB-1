package com.example.sistemaprodutos.servlets;

import com.example.sistemaprodutos.ProdutosRepositorio;
import com.example.sistemaprodutos.Usuario;
import com.example.sistemaprodutos.Produto;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/removerproduto")
public class RemoverProduto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>🗑️ Remover Produto</h1>");

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado.</p>");
            out.println("<a href='index.html'>Fazer Login</a>");
            out.println("</body></html>");
            return;
        }

        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        if (!u.isFuncionario()) {
            out.println("<p>❌ Apenas funcionários podem remover produtos.</p>");
            out.println("<a href='menu.html'>Voltar</a>");
            out.println("</body></html>");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            out.println("<p>❌ ID do produto não especificado.</p>");
            out.println("<a href='produtos'>← Voltar</a>");
            out.println("</body></html>");
            return;
        }

        int id = Integer.parseInt(idStr);

        // Busca o produto antes de remover para exibir informações
        Produto p = ProdutosRepositorio.buscarProduto(id);
        String nomeProduto = (p != null) ? p.getNome() : "ID " + id;

        boolean ok = ProdutosRepositorio.removerProduto(id);

        if (ok) {
            out.println("<p>✅ <strong>Produto removido com sucesso!</strong></p>");
            out.println("<p>📦 Produto removido: " + nomeProduto + "</p>");
        } else {
            out.println("<p>❌ Produto não encontrado.</p>");
        }

        out.println("<hr>");
        out.println("<p><a href='produtos'>← Ver Produtos</a></p>");
        out.println("</body></html>");
    }
}