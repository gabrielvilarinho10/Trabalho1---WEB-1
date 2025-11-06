package com.example.sistemaprodutos.servlets;

import com.example.sistemaprodutos.ProdutosRepositorio;
import com.example.sistemaprodutos.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/logar")
public class Logar extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        Usuario u = ProdutosRepositorio.buscarUsuarioPorLogin(login);
        response.setContentType("text/html;charset=UTF-8");

        if (u == null || !u.getSenha().equals(senha)) {
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>❌ Erro no Login</h1>");
            out.println("<p>Login ou senha inválidos.</p>");
            out.println("<a href='index.html'>Voltar</a>");
            out.println("</body></html>");
            return;
        }

        // Login bem-sucedido
        HttpSession sessao = request.getSession();
        sessao.setAttribute("usuarioLogado", u);

        // Redireciona para o menu
        response.sendRedirect("menu.html");
    }
}