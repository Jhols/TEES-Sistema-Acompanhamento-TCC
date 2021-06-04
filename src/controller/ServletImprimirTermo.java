package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InscricaoProjetoDAO;
import dao.LoginDAO;
import model.Aluno;
import model.InscricaoProjeto;

@WebServlet(name = "ImprimirTermo", urlPatterns = {"/ImprimirTermo"})
public class ServletImprimirTermo  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// Tentar pegar o aluno que estÃ¡ logado atualmente
		var aluno = (Aluno) request.getSession().getAttribute("pessoa");
		if (aluno == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		// ao entrar nessa pagina o parametro 'inscricao' deve indicar o id da inscricao cujo termo serÃ¡ impresso 
		int idInscricao = Integer.parseInt(request.getParameter("inscricao"));
		// busca a inscricao no banco pelo id passado por parametro
		InscricaoProjeto inscricao = InscricaoProjetoDAO.getInstance().pesquisarInscricaoPorId(idInscricao);
		
		// para mostrar a data de hoje no termo
		LocalDateTime ldt = LocalDateTime.now();
		
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
				+ "    <title>Imprimir Termo de aceitação</title>\r\n"
				+ "\r\n"
				+ "    <!-- Custom fonts for this template -->\r\n"
				+ "    <link href=\"resources/bootstrap/vendor/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\">\r\n"
				+ "    <link\r\n"
				+ "        href=\"https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i\"\r\n"
				+ "        rel=\"stylesheet\">\r\n"
				+ "\r\n"
				+ "    <!-- Custom styles for this template -->\r\n"
				+ "    <link href=\"resources/bootstrap/css/sb-admin-2.min.css\" rel=\"stylesheet\">\r\n"
				+ "\r\n"
				+ "    <!-- Custom styles for this page -->\r\n"
				+ "    <link href=\"resources/bootstrap/vendor/datatables/dataTables.bootstrap4.min.css\" rel=\"stylesheet\">\r\n"
				+ "\r\n"
				+ "</head>\r\n"
				+ "\r\n"
				+ "<body id=\"page-top\">\r\n"
				+ "\r\n"
				+ "    <!-- Page Wrapper -->\r\n"
				+ "    <div id=\"wrapper\" class=\"card o-hidden border-0 my-5\">\r\n"
				+ "    \r\n"
				+ "        <!-- Content Wrapper -->\r\n"
				+ "        <div id=\"content-wrapper\" class=\"card-body\">\r\n"
				+ "\r\n"
				+ "            <!-- Main Content -->\r\n"
				+ "            <div id=\"content\">\r\n"
				+ "\r\n"
				+ "                <!-- Begin Page Content -->\r\n"
				+ "                <div class=\"container-fluid\">\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "                    <!-- DataTales Example -->\r\n"
				+ "                    <div class=\"card mb-4\" style=\"width:100%; margin:auto\">\r\n"
				+ "                        \r\n"
				+ "                        <div class=\"card-body\">\r\n"
				+ "                            <div class=\"table-responsive\">\r\n"
				+ "                                <div class=\"text-center\" style=\"font-size: x-large\">\r\n"
				+ "                                <br />\r\n"
				+ "                                <br />\r\n"
				+ "                                <p>CARTA DE ACEITAÇÃO DE ORIENTAÇÃO </p>	\r\n"
				+ "								<br />\r\n"
				+ "								<br />\r\n"
				+ "								<br />\r\n"
				+ "							<p style=\"text-align:justify; line-height:2\">Eu, "
				+ inscricao.getProjeto().getProfessor().getNome()
				+ ", professor da Universidade do Estado da Bahia (UNEB), \r\n"
				+ "e-mail: "
				+ inscricao.getProjeto().getProfessor().getEmail()
				+ " comprometo-me a orientar o Trabalho de Conclusão de Curso com título provisório: \r\n"
				+ inscricao.getProjeto().getTitulo()
				+ " que será executado pelo(a) aluno(a) "
				+ inscricao.getAluno().getNome()
				+ ". Confirmo que irei estar disponível \r\n"
				+ "de forma regular para orientar o(a) referido(a) aluno até a conclusão deste projeto, seguindo todas as normas\r\n"
				+ "definidas nas disciplinas Trabalho de Conclusão de Curso I e II. Desta forma, assino este documento como prova \r\n"
				+ "do compromisso assumido.   \r\n"
				+ "							</p>\r\n"
				+ "							 <br />\r\n"
				+ "							 <br />\r\n"
				+ "							<p>\r\n"
				+ "							Salvador, "+ldt.getDayOfMonth()+" de "+ldt.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())+" de "+ldt.getYear()+ " \r\n"
				+ "                                </p>\r\n"
				+ "                                <br /><br />\r\n"
				+ "                            <table style=\"margin:auto\">\r\n"
				+ "                            <tr><td>__________________________<td width=50><td>__________________________<td>\r\n"
				+ "                            <tr><td>Assinatura do Orientador<td><td>Assinatura do Discente<td>\r\n"
				+ "                            \r\n"
				+ "                            </table>\r\n"
				+ "                             <br /><br />\r\n"
				+ "                            \r\n"
				+ "                            </div>\r\n"
				+ "                        </div>\r\n"
				+ "                    </div>\r\n"
				+ "\r\n"
				+ "                </div>\r\n"
				+ "                <!-- /.container-fluid -->\r\n"
				+ "\r\n"
				+ "            </div>\r\n"
				+ "            <!-- End of Main Content -->\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "        </div>\r\n"
				+ "        <!-- End of Content Wrapper -->\r\n"
				+ "\r\n"
				+ "    </div>\r\n"
				+ "    <!-- End of Page Wrapper -->\r\n"
				+ "\r\n"
				+ "    <!-- Scroll to Top Button-->\r\n"
				+ "    <a class=\"scroll-to-top rounded\" href=\"#page-top\">\r\n"
				+ "        <i class=\"fas fa-angle-up\"></i>\r\n"
				+ "    </a>\r\n"
				+ "\r\n"
				+ "    <!-- Logout Modal-->\r\n"
				+ "    <div class=\"modal fade\" id=\"logoutModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\"\r\n"
				+ "        aria-hidden=\"true\">\r\n"
				+ "        <div class=\"modal-dialog\" role=\"document\">\r\n"
				+ "            <div class=\"modal-content\">\r\n"
				+ "                <div class=\"modal-header\">\r\n"
				+ "                    <h5 class=\"modal-title\" id=\"exampleModalLabel\">Ready to Leave?</h5>\r\n"
				+ "                    <button class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n"
				+ "                        <span aria-hidden=\"true\">Ã—</span>\r\n"
				+ "                    </button>\r\n"
				+ "                </div>\r\n"
				+ "                <div class=\"modal-body\">Select \"Logout\" below if you are ready to end your current session.</div>\r\n"
				+ "                <div class=\"modal-footer\">\r\n"
				
				+ "                </div>\r\n"
				+ "            </div>\r\n"
				
				+ "        </div>\r\n"
				
				+ "    </div>\r\n"
				
				+ "\n<a class= \"btn btn-primary\" align=\"center\" href= \"alunoDashboard\" role=\"button\">Voltar</a>\r\n"
				+ "\n<a class= \"btn btn-primary\" align=\"center\" href= \"login.html\" role=\"button\">Login</a>\r\n"
				
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
				+ "    <!-- Page level plugins -->\r\n"
				+ "    <script src=\"resources/bootstrap/vendor/datatables/jquery.dataTables.min.js\"></script>\r\n"
				+ "    <script src=\"resources/bootstrap/vendor/datatables/dataTables.bootstrap4.min.js\"></script>\r\n"
				+ "\r\n"
				+ "    <!-- Page level custom scripts -->\r\n"
				+ "    <script src=\"resources/bootstrap/js/demo/datatables-demo.js\"></script>\r\n"
				+ "    \r\n"
				+ "    <script>\r\n"
				+ "    	window.print();\r\n"
				+ "    </script>\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "\r\n"
				+ "</html>";
		
		response.getWriter().write(html);
	}

}
