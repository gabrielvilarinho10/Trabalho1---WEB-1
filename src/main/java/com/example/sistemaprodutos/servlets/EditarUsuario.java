package com.example.sistemaprodutos.servlets;
import com.example.sistemaprodutos.Usuario;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/editarusuario")
public class EditarUsuario extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>✏️ Editar Meu Perfil</h1>");
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado para editar seu perfil.</p>");
            out.println("<p><a href='index.html'>Fazer Login</a></p>");
            out.println("</body></html>");
            return;
        }
        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        out.println("<p>👤 Editando perfil de: <strong>" + u.getNome() + "</strong></p>");
        out.println("<hr>");
        out.println("<form method='post' action='editarusuario'>");
        out.println("<label>Nome:</label><br>");
        out.println("<input name='nome' value='" + u.getNome() + "' required/><br><br>");
        out.println("<label>Senha:</label><br>");
        out.println("<input name='senha' type='password' value='" + u.getSenha() + "' required/><br><br>");
        out.println("<button type='submit'>💾 Salvar Alterações</button>");
        out.println("</form>");
        out.println("<hr>");
        out.println("<p><a href='menu.html'>← Voltar ao Menu</a></p>");
        out.println("</body></html>");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession sessao = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>✏️ Editar Perfil</h1>");
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado.</p>");
            out.println("<a href='index.html'>Fazer Login</a>");
            out.println("</body></html>");
            return;
        }
        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        if (nome != null && !nome.isBlank()) u.setNome(nome);
        if (senha != null && !senha.isBlank()) u.setSenha(senha);
        out.println("<p>✅ <strong>Dados atualizados com sucesso!</strong></p>");
        out.println("<p>Nome: " + u.getNome() + "</p>");
        out.println("<hr>");
        out.println("<p><a href='menu.html'>← Voltar ao Menu</a></p>");
        out.println("</body></html>");
    }
}