package com.example.sistemaprodutos.servlets;

import com.example.sistemaprodutos.ProdutosRepositorio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cadastrarusuario")
public class CadastrarUsuario extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        boolean ok = ProdutosRepositorio.cadastrarUsuario(nome, login, senha, "cliente");

        out.println("<html><body>");
        out.println("<h1>📝 Cadastro de Cliente</h1>");

        if (!ok) {
            out.println("<p>❌ <strong>Erro:</strong> Login já existe. Escolha outro login.</p>");
            out.println("<a href='cadastro.html'>← Voltar e tentar novamente</a>");
        } else {
            out.println("<p>✅ <strong>Cadastro realizado com sucesso!</strong></p>");
            out.println("<p>Agora você pode fazer login e começar a comprar.</p>");
            out.println("<a href='index.html'>Fazer Login</a> | <a href='menu.html'>Ir ao Menu</a>");
        }

        out.println("</body></html>");
    }
}