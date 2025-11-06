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
@WebServlet("/editarproduto")
public class EditarProduto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sessao = request.getSession(false);
        out.println("<html><body>");
        out.println("<h1>✏️ Editar Produto</h1>");
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado.</p>");
            out.println("<a href='index.html'>Fazer Login</a>");
            out.println("</body></html>");
            return;
        }
        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        if (!u.isFuncionario()) {
            out.println("<p>❌ Apenas funcionários podem editar produtos.</p>");
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
        Produto p = ProdutosRepositorio.buscarProduto(id);
        if (p == null) {
            out.println("<p>❌ Produto não encontrado.</p>");
            out.println("<a href='produtos'>← Voltar</a>");
            out.println("</body></html>");
            return;
        }
        out.println("<p>Editando produto: <strong>" + p.getNome() + "</strong></p>");
        out.println("<hr>");
        out.println("<form method='post' action='editarproduto'>");
        out.println("<input type='hidden' name='id' value='" + p.getId() + "'/>");
        out.println("<label>Nome:</label><br>");
        out.println("<input name='nome' value='" + p.getNome() + "' required/><br><br>");
        out.println("<label>Preço:</label><br>");
        out.println("<input name='preco' type='number' step='0.01' value='" + p.getPreco() + "' required/><br><br>");
        out.println("<button type='submit'>💾 Salvar</button>");
        out.println("</form>");
        out.println("<hr>");
        out.println("<p><a href='produtos'>← Cancelar</a></p>");
        out.println("</body></html>");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession sessao = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>✏️ Editar Produto</h1>");
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<p>❌ Você precisa estar logado.</p>");
            out.println("<a href='index.html'>Fazer Login</a>");
            out.println("</body></html>");
            return;
        }
        Usuario u = (Usuario) sessao.getAttribute("usuarioLogado");
        if (!u.isFuncionario()) {
            out.println("<p>❌ Apenas funcionários.</p>");
            out.println("<a href='menu.html'>Voltar</a>");
            out.println("</body></html>");
            return;
        }
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");
        int id = Integer.parseInt(idStr);
        double preco = 0.0;
        try {
            preco = Double.parseDouble(precoStr);
        } catch (Exception e) {
            out.println("<p>❌ Preço inválido.</p>");
            out.println("<a href='produtos'>← Voltar</a>");
            out.println("</body></html>");
            return;
        }
        boolean ok = ProdutosRepositorio.editarProduto(id, nome, preco);
        if (ok) {
            out.println("<p>✅ <strong>Produto atualizado com sucesso!</strong></p>");
            out.println("<p>📦 Nome: " + nome + "</p>");
            out.println("<p>💰 Preço: R$ " + String.format("%.2f", preco) + "</p>");
        } else {
            out.println("<p>❌ Erro ao atualizar produto.</p>");
        }
        out.println("<hr>");
        out.println("<p><a href='produtos'>← Ver Produtos</a></p>");
        out.println("</body></html>");
    }
}