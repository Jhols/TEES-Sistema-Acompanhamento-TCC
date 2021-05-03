package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDAO;
import model.Professor;


@WebServlet(name = "dashboardProfessor", urlPatterns = {"/professorDashboard"})
public class ServletDashboardProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		var professor = (Professor) request.getSession().getAttribute("pessoa");
		
		if (professor == null) {
			System.out.println("login automatico");
			professor = (Professor) LoginDAO.pesquisaPessoa("alexandre", "1234");
			request.getSession().setAttribute("pessoa", professor);
		}
		
		System.out.println(professor);
		// * Permissões para Funcionalidades *
		boolean cadastroProfOrientador = ! professor.isOrientador();
		boolean cadastroProjeto = professor.isOrientador();
		boolean alunosCandidatos = professor.isOrientador();
		boolean projetosComOrientandos = professor.isOrientador();
		boolean visualizarProjetosProfessor = professor.isOrientador();
		
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
		+ "                <div class=\"sidebar-brand-text mx-3\">PERFIL PROFESSOR</div>\r\n"
		+ "            </a>\r\n"
		+ "\r\n"
		+ "            <!-- Divider -->\r\n"
		+ "            <hr class=\"sidebar-divider my-0\">\r\n"
		
		+ "\r\n"
		+ "            \r\n"
		+ "            <!-- Divider -->\r\n"
		+ "            <hr class=\"sidebar-divider\">\r\n"
		+ "\r\n"
		+ "                   \r\n"
		+ "\r\n";
		if (cadastroProfOrientador) {
			html += "           <!-- Item Cadastro Professor Orientador -->\r\n"
			+ "            <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"charts.html\">\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span style=\"text-align:center\">Cadastro professor orientador</span></a>\r\n"
			+ "            </li>\r\n";
		}
		if (cadastroProjeto) {
			html += "           <!-- Item cadastrar projetos -->\r\n"
			+ "            <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"cadProjeto\">\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span>Cadastrar projeto</span></a>\r\n"
			+ "            </li>\r\n";
		}
		if (visualizarProjetosProfessor) {
			html += "           <!-- Item cadastrar projetos -->\r\n"
					+ "            <li class=\"nav-item\">\r\n"
					+ "                <a class=\"nav-link\" href=\"projetosProfessor?opcao=listar\">\r\n"
					+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
					+ "                    <span>Projetos do Professor</span></a>\r\n"
					+ "            </li>\r\n";
		}
		if (alunosCandidatos) {
			html += "            <!-- Item alunos candidatos -->\r\n"
			+ "            <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"charts.html\">\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span>Alunos candidatos</span></a>\r\n"
			+ "            </li>\r\n";
		}
		if (projetosComOrientandos) {
			html += "            <!-- Item alunos candidatos -->\r\n"
			+ "			 <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"charts.html\">\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span>Projetos com orientandos</span></a>\r\n"
			+ "            </li>\r\n";
		}
		html += "        </ul>\r\n"
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
		+ "\r\n"
		+ "</body>\r\n";
		
		
		if ("OK".equals(request.getParameter("cadastroProjeto"))) {
			html += "<script>alert(\"Você cadastrou seu projeto com sucesso!\");</script>";
		}
		
		html += "\r\n"
		+ "</html>";
		
		writer.write(html);
	}

}