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

@WebServlet("/cadastrarfuncionario")
public class CadastrarFuncionario extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>👤 Cadastro de Funcionário</h1>");

        // Verifica se está logado
        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado como funcionário.</p>");
            out.println("<a href='index.html'>Fazer Login</a>");
            out.println("</body></html>");
            return;
        }

        // Verifica se é funcionário
        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        if (!u.isFuncionario()) {
            out.println("<p>❌ Apenas funcionários podem cadastrar outros funcionários.</p>");
            out.println("<a href='menu.html'>Voltar ao Menu</a>");
            out.println("</body></html>");
            return;
        }

        // Processa o cadastro
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        boolean ok = ProdutosRepositorio.cadastrarFuncionario(nome, login, senha);

        if (!ok) {
            out.println("<p>❌ Login já existe. Escolha outro login.</p>");
            out.println("<a href='cadastro_funcionario.html'>Voltar</a>");
        } else {
            out.println("<p>✅ Funcionário cadastrado com sucesso!</p>");
            out.println("<a href='menu.html'>Voltar ao Menu</a>");
        }

        out.println("</body></html>");
    }
}