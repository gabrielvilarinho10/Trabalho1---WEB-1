package com.example.sistemaprodutos.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/sair")
public class Sair extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Invalida a sessão
        HttpSession sessao = request.getSession(false);
        if (sessao != null) {
            sessao.invalidate();
        }

        // Exibe mensagem de logout
        out.println("<html><body>");
        out.println("<h1>🚪 Logout</h1>");
        out.println("<p>✅ <strong>Logout realizado com sucesso!</strong></p>");
        out.println("<p>Você saiu do sistema.</p>");
        out.println("<hr>");
        out.println("<p><a href='menu.html'>← Voltar ao Menu</a></p>");
        out.println("</body></html>");
    }
}