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

@WebServlet("/adicionarcarrinho")
public class AdicionarCarrinho extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>🛒 Adicionar ao Carrinho</h1>");

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado para adicionar produtos ao carrinho.</p>");
            out.println("<p><a href='index.html'>Fazer Login</a></p>");
            out.println("</body></html>");
            return;
        }

        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            out.println("<p>❌ Produto não especificado.</p>");
            out.println("<a href='produtos'>← Voltar aos Produtos</a>");
            out.println("</body></html>");
            return;
        }

        int id = Integer.parseInt(idStr);
        Produto p = ProdutosRepositorio.buscarProduto(id);

        if (p != null) {
            u.adicionarAoCarrinho(p);
            out.println("<p>✅ <strong>Produto adicionado ao carrinho com sucesso!</strong></p>");
            out.println("<p>📦 Produto: <strong>" + p.getNome() + "</strong></p>");
            out.println("<p>💰 Preço: R$ " + String.format("%.2f", p.getPreco()) + "</p>");
        } else {
            out.println("<p>❌ Produto não encontrado.</p>");
        }

        out.println("<hr>");
        out.println("<p>");
        out.println("<a href='produtos'>🛍️ Continuar Comprando</a> | ");
        out.println("<a href='vercarrinho'>🛒 Ver Meu Carrinho</a>");
        out.println("</p>");
        out.println("</body></html>");
    }
}