package com.example.sistemaprodutos.servlets;

import com.example.sistemaprodutos.Usuario;
import com.example.sistemaprodutos.Produto;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/vercarrinho")
public class VerCarrinho extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>🛒 Meu Carrinho de Compras</h1>");

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado para ver o carrinho.</p>");
            out.println("<p><a href='index.html'>Fazer Login</a> | <a href='menu.html'>Voltar ao Menu</a></p>");
            out.println("</body></html>");
            return;
        }

        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        out.println("<p>👤 Cliente: <strong>" + u.getNome() + "</strong></p>");
        out.println("<hr>");

        if (u.getCarrinho().isEmpty()) {
            out.println("<p>😕 Seu carrinho está vazio.</p>");
            out.println("<p><a href='produtos'>🛍️ Ver Produtos</a> para adicionar itens</p>");
        } else {
            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr style='background-color: #f0f0f0;'>");
            out.println("<th>ID</th><th>Produto</th><th>Preço</th><th>Ação</th>");
            out.println("</tr>");

            for (Produto p : u.getCarrinho()) {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td><strong>" + p.getNome() + "</strong></td>");
                out.println("<td style='color: green;'><strong>R$ " + String.format("%.2f", p.getPreco()) + "</strong></td>");
                out.println("<td><a href='removercarrinho?id=" + p.getId() + "'>🗑️ Remover</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<hr>");
            out.println("<h3>💰 Valor Total: R$ " + String.format("%.2f", u.valorTotalCarrinho()) + "</h3>");
            out.println("<p><em>Itens no carrinho: " + u.getCarrinho().size() + "</em></p>");
        }

        out.println("<hr>");
        out.println("<p>");
        out.println("<a href='produtos'>🛍️ Continuar Comprando</a> | ");
        out.println("<a href='menu.html'>← Voltar ao Menu</a>");
        out.println("</p>");
        out.println("</body></html>");
    }
}