package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InscricaoProjetoDAO;
import dao.ProjetoDAO;
import dao.TurmaDAO;
import enums.SituacaoInscricao;
import model.Aluno;
import model.InscricaoProjeto;
import model.Projeto;


@WebServlet(name = "dashboardAluno", urlPatterns = {"/alunoDashboard"})
public class ServletDashboardAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Tentar pegar o aluno que está logado atualmente
		var aluno = (Aluno) request.getSession().getAttribute("pessoa");
		if (aluno == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		System.out.println("ALUNO LOGADO "+aluno);
		boolean imprimirTermoDeAceite = false; // Condição para mostar o botão de imprimir termo de aceite no menu do aluno
		InscricaoProjeto inscricao = null;
		// busca as inscrições do aluno cuja situação seja 'ASSOCIADO'
		var inscricoesAssociadas = InscricaoProjetoDAO.getInstance().pesquisarInscricoesPorAluno(aluno.getIdAluno(), SituacaoInscricao.ASSOCIADO);
		System.out.println("INSCRICOES DO ALUNO: "+inscricoesAssociadas);
		
		if (inscricoesAssociadas.size() > 0) {
			// se houver inscricão 'associado' permitir que o botão de imprimir termo apareça no menu
			inscricao = inscricoesAssociadas.get(0);
			imprimirTermoDeAceite = true;
		}
		
	   
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		var writer = response.getWriter();			
		String html = "<!DOCTYPE html>\r\n"
			+ "<html lang=\"pt\">\r\n"
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
			+ "                <div class=\"sidebar-brand-text mx-3\">PERFIL ALUNO</div>\r\n"
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
			+ "\r\n"
			+ "            <!-- Nav Item - Pages Collapse Menu -->\r\n"
			+ "           <!-- Nav Item - Procurar Projetos Disponiveis -->\r\n"
			+ "            <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"ProjetoServlet?opcao=listar\">\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span style=\"text-align:center\">Projetos Disponíveis</span></a>\r\n"
			+ "            </li>\r\n"
			+ "\r\n"
			+ "                \r\n"
			+ "\r\n";
		
		// só concatena html do botão de imprimir termo se houver inscricao associada (como visto acima) 
		if (imprimirTermoDeAceite) {
			html += "           <!-- Item - Imprimir Termo de Aceite -->\r\n"
			+ "            <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"ImprimirTermo?inscricao="+inscricao.getId()+ "\">\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span>Termo de Aceite</span></a>\r\n"
			+ "            </li>\r\n"
			+ "            \r\n";
		}
			html += "			 <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"InscricaoProjetoServlet?opcao=buscar\">\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span>Meu Projeto</span></a>\r\n"
			+ "            </li>\r\n";
			
			html += "			 <li class=\"nav-item\">\r\n"
					+ "                <a class=\"nav-link\" href=\"visualizarAnexosDeTcc?turma="+TurmaDAO.getInstance().pesquisarTurmaDoAluno(aluno.getIdAluno()) +"\" >\r\n"
					+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
					+ "                    <span>Visualizar Anexos de Tcc</span></a>\r\n"
					+ "            </li>\r\n";
		if (inscricao != null) {
			html += "			 <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"visualizarAnexosProjetos?idProjeto="+inscricao.getIdProjeto() +"\" >\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span>Visualizar Anexos do projeto</span></a>\r\n"
			+ "            </li>\r\n";
		}
			
			html += "			 <li class=\"nav-item\">\r\n"
			+ "                <a class=\"nav-link\" href=\"editAluno\">\r\n"
			+ "                    <i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "                    <span>Editar Cadastro</span></a>\r\n"
			+ "            </li>\r\n"
			
			
			+ "	<li class=\"nav-item\">\r\n"
			+ "<i class=\"fas fa-fw fa-wrench\"></i>\r\n"
			+ "<a class= \"btn btn-primary\" align=\"center\" href= \"login.html\" role=\"button\">Voltar</a>\r\n"
			+ "	</li>\r\n"
			
			+ "        </ul>\r\n"
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
			
			if ("OK".equals(request.getParameter("editaAluno"))) {
				html += "<script>alert(\"Você editou seu cadastro com sucesso!\");</script>";
			}
			
			html += "\r\n"
			+ "</html>";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		writer.write(html);
	}

}