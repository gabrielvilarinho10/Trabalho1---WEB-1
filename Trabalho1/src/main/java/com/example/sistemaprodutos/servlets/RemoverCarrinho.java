package com.example.sistemaprodutos.servlets;

import com.example.sistemaprodutos.Usuario;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/removercarrinho")
public class RemoverCarrinho extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>🗑️ Remover do Carrinho</h1>");

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado.</p>");
            out.println("<a href='index.html'>Fazer Login</a>");
            out.println("</body></html>");
            return;
        }

        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            out.println("<p>❌ Produto não especificado.</p>");
            out.println("<a href='vercarrinho'>← Voltar ao Carrinho</a>");
            out.println("</body></html>");
            return;
        }

        int id = Integer.parseInt(idStr);
        boolean ok = u.removerDoCarrinho(id);

        if (ok) {
            out.println("<p>✅ Produto removido do carrinho com sucesso!</p>");
        } else {
            out.println("<p>❌ Produto não encontrado no carrinho.</p>");
        }

        out.println("<hr>");
        out.println("<p><a href='vercarrinho'>🛒 Ver Carrinho</a> | <a href='produtos'>🛍️ Continuar Comprando</a></p>");
        out.println("</body></html>");
    }
}