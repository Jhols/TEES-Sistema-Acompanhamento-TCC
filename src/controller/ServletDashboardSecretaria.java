package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Secretaria;


@WebServlet( urlPatterns = {"/secretariaDashboard"})
public class ServletDashboardSecretaria extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Tentar pegar o secretaria que est� logado atualmente
		var secretaria = (Secretaria) request.getSession().getAttribute("pessoa");
		
		if (secretaria == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		System.out.println(secretaria);
		// * Permiss�es para Funcionalidades *
		
		
		// verificar se h� alguma a��o a ser executada pelo servlet
		if (verificarAcao(request, response)) {
			// se houve alguma acao a ser executada nesse servlet
			// e a pagina foi redirecionada, para a execu��o da fun��o
			return;
		}
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		var writer = response.getWriter();
		
			
		String html = "<!DOCTYPE html>\r\n"
		+ "<html lang=\"en\">\r\n"
		+ "\r\n"
		+ "<head>\r\n"
		+ "\r\n"
		+ "    <meta charset=\"utf-8\">\r\n"
		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n"
		+ "    <meta name=\"description\" content=\"\">\r\n"
		+ "    <meta name=\"author\" content=\"\">\r\n"
		+ "\r\n"
		+ "    \r\n"
		+ "\r\n"
		+ "    <!-- Custom fonts for this template-->\r\n"
		+ "    <link href=\"resources/bootstrap/vendor/fontawesome-free/css/all.css\" rel=\"stylesheet\" type=\"text/css\">\r\n"
		+ "    <link\r\n"
		+ "        href=\"https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i\"\r\n"
		+ "        rel=\"stylesheet\">\r\n"
		+ "\r\n"
		+ "    <!-- Custom styles for this template-->\r\n"
		+ "    <link href=\"resources/bootstrap/css/sb-admin-2.css\" rel=\"stylesheet\">\r\n"
		+ "\r\n"
		+ "</head>\r\n"
		+ "\r\n"
		+ "<body id=\"page-top\">\r\n"
		+ "\r\n"
		+ "    <!-- Page Wrapper -->\r\n"
		+ "    <div id=\"wrapper\">\r\n"
		+ "\r\n"
		+ "        <!-- Sidebar -->\r\n"
		+ "        <ul class=\"navbar-nav bg-gradient-primary sidebar sidebar-dark accordion\" id=\"accordionSidebar\">\r\n"
		+ "\r\n"
		+ "            <!-- Sidebar - Brand -->\r\n"
		+ "            <a class=\"sidebar-brand d-flex align-items-center justify-content-center\" href=\"index.html\">\r\n"
		+ "                <div class=\"sidebar-brand-icon rotate-n-15\">\r\n"
		+ "                    <i class=\"fas fa-laugh-wink\"></i>\r\n"
		+ "                </div>\r\n"
		+ "                <div class=\"sidebar-brand-text mx-3\">PERFIL SECRET�RIO(A)</div>\r\n"
		+ "            </a>\r\n"
		+ "\r\n"
		+ "            <!-- Divider -->\r\n"
		+ "            <hr class=\"sidebar-divider my-0\">\r\n"
		
		+ "\r\n"
		+ "            \r\n"
		+ "            <!-- Divider -->\r\n"
		+ "\r\n"
		+ "                   \r\n"
		+ "\r\n"
		+ "           <!-- Item Cadastro Professor Orientador -->\r\n"
		+ "            <li class=\"nav-item\">\r\n"
		+ "                <a class=\"nav-link\" href=\"candidatos_orientador.jsp\">\r\n"
		+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
		+ "                    <span style=\"text-align:center\">Candidatura de professor(a) orientador(a)</span></a>\r\n"
		+ "            </li>\r\n"
		
		+ "           <!-- Item Cadastro Professor Orientador -->\r\n"
		+ "            <li class=\"nav-item\">\r\n"
		+ "                <a class=\"nav-link\" href=\"cadTurmasTcc\">\r\n"
		+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
		+ "                    <span style=\"text-align:center\">Cadastrar turma de TCC</span></a>\r\n"
		+ "            </li>\r\n"
		
		+ "           <!-- Item Cadastro Professor Orientador -->\r\n"
		+ "            <li class=\"nav-item\">\r\n"
		+ "                <a class=\"nav-link\" href=\"exibirTurma\">\r\n"
		+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"

		+ "                    <span style=\"text-align:center\">Visualizar Turmas de TCC</span></a>\r\n"

		+ "            </li>\r\n"
	
		+ "           <!-- Item Cadastro Professor Orientador -->\r\n"
		+ "            <li class=\"nav-item\">\r\n"
		+ "                <a class=\"nav-link\" href=\"excluirTurma\">\r\n"
		+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
		+ "                    <span style=\"text-align:center\">Excluir turmas de TCC</span></a>\r\n"
		+ "            </li>\r\n"
		
		+ "           <!-- Item Cadastro Professor Orientador -->\r\n"
		+ "            <li class=\"nav-item\">\r\n"
		+ "                <a class=\"nav-link\" href=\"VisualizarTodosProjetos?opcao=pesquisarProjetos&prf=1\">\r\n"
		+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
		+ "                    <span style=\"text-align:center\">Visualizar projetos  </span></a>\r\n"
		+ "            </li>\r\n"
		
		+ "   <!-- botao -->\r\n"
		+ "	<li class=\"nav-item\">\r\n"
		+"<i class=\"fas fa-fw fa-wrench\"></i>\r\n"
		+ "<a class= \"btn btn-primary\" align=\"center\" href= \"login.html\" role=\"button\">Sair</a>\r\n"
				
		+ "</li>\r\n"
		+ "  </ul>\r\n"
		+ "       \r\n"
		+ "</div>\r\n"
		+ "    \r\n"
		+ "\r\n"
		+ "         \r\n"
		+ "   \r\n"
		+ "\r\n"
		+ "    <!-- Bootstrap core JavaScript-->\r\n"
		+ "    <script src=\"resources/bootstrap/vendor/jquery/jquery.min.js\"></script>\r\n"
		+ "    <script src=\"resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>\r\n"
		+ "\r\n"
		+ "    <!-- Core plugin JavaScript-->\r\n"
		+ "    <script src=\"resources/bootstrap/vendor/jquery-easing/jquery.easing.min.js\"></script>\r\n"
		+ "\r\n"
		+ "    <!-- Custom scripts for all pages-->\r\n"
		+ "    <script src=\"resources/bootstrap/js/sb-admin-2.min.js\"></script>\r\n"
		+ "\r\n";
		
		// mostrar alertas para a��es executadas pelo servlet
		if ("ok".equals(request.getParameter("turma"))) {
			html += "<script>alert(\"Cadastro de turma efetuado com sucesso!\");</script>";
		}
		
		html += "</body>\r\n"
		+ "\r\n"
		+ "</html>";
		
		writer.write(html);
	}
	
	// verifica os parametros da pagina para saber se h� alguma a��o a ser executada pelo servlet
	// retorna true se a a��o redireciona a pagina
	private boolean verificarAcao(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String acao= request.getParameter("acao");
		if(acao==null)
		{
			return false;
		}
		switch (acao) {
		case "gerar":
			
			return true;
		}
		return false;
	}

}