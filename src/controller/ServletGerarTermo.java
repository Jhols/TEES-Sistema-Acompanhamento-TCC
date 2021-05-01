package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AlunoDAO;
import dao.LoginDAO;
import dao.ProjetoDAO;
import model.Aluno;
import model.Professor;
import model.Projeto;

@WebServlet(name = "GerarTermo", urlPatterns = {"/GerarTermo"})
public class ServletGerarTermo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		var professor = (Professor) request.getSession().getAttribute("pessoa");
		if (professor == null) {
			System.out.println("login automatico");
			professor = (Professor) LoginDAO.pesquisaPessoa("alexandre", "1234");
			request.getSession().setAttribute("pessoa", professor);
		}
		
		
		int idAluno= Integer.parseInt(request.getParameter("aluno"));
		int idProjeto= Integer.parseInt(request.getParameter("idProjeto")); 
		
		Aluno aluno= AlunoDAO.pesquisarAlunoPorIdAluno(idAluno);
		Projeto projeto=ProjetoDAO.pesquisarProjetoPorIdProjeto(idProjeto);
		
		LocalDateTime ldt = LocalDateTime.now();
		
		String html = "\r\n"
				+ "<!DOCTYPE html>\r\n"
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
				+ "    <title>Alunos Candidatos</title>\r\n"
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
				+ "    <div id=\"wrapper\" class=\"card o-hidden border-0 shadow-lg my-5\">\r\n"
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
				+ "                    <div class=\"card shadow mb-4\" style=\"width:60%; margin:auto\">\r\n"
				+ "                        \r\n"
				+ "                        <div class=\"card-body\">\r\n"
				+ "                            <div class=\"table-responsive\">\r\n"
				+ "                                <div class=\"text-center\">\r\n"
				+ "                                <p>CARTA DE ACEITAÇÃO DE ORIENTAÇÃO </p>	\r\n"
				+ "\r\n"
				+ "							<p style=\"text-align:justify\">Eu, "
				+ "<b>"+ professor.getNome()+ "</b>"
				
				+ ", professor da Universidade do Estado da Bahia (UNEB), \r\n"
				+ "e-mail: "
				+ "<b>"+ professor.getEmail()  + "</b>"
				+ " comprometo-me a orientar o Trabalho de Conclusão de Curso com título provisório: \r\n"
				+ "<b>"+ projeto.getTitulo() +"</b>"
				+ " que será executado pelo(a) aluno(a) "
				+ "<b>"+ aluno.getNome() +"</b>"
				+ ". Confirmo que irei estar disponível \r\n"
				+ "							de forma regular para orientar o(a) referido(a) aluno até a conclusão deste projeto, seguindo todas as normas\r\n"
				+ "							definidas nas disciplinas Trabalho de Conclusão de Curso I e II. Desta forma, assino este documento como prova \r\n"
				+ "							do compromisso assumido.   \r\n"
				+ "							</p>\r\n"
				+ "							 \r\n"
				+ "							<p>\r\n"
				+ "							Salvador, "+ldt.getDayOfMonth()+" de "+ldt.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())+" de "+ldt.getYear()+". \r\n"
				+ "                                </p>\r\n"
				+ "                                <br /><br />\r\n"
				+ "                            <table style=\"margin:auto\">\r\n"
				+ "                            <tr><td>__________________________<td width=50><td>__________________________<td>\r\n"
				+ "                            <tr><td>Assinatura do Orientador<td><td>Assinatura do Discente<td>\r\n"
				+ "                            \r\n"
				+ "                            </table>\r\n"
				+ "                             <br /><br />\r\n"
				+ "                            <a class= \"btn btn-primary\" href= \"professorDashboard?acao=gerar&aluno="+aluno.getIdAluno()+"&projeto="+projeto.getId()+  "\" role=\"button\">Gerar termo</a>\r\n"
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
				+ "                        <span aria-hidden=\"true\">×</span>\r\n"
				+ "                    </button>\r\n"
				+ "                </div>\r\n"
				+ "                <div class=\"modal-body\">Select \"Logout\" below if you are ready to end your current session.</div>\r\n"
				+ "                <div class=\"modal-footer\">\r\n"
				+ "                    <button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancel</button>\r\n"
				+ "                    <a class=\"btn btn-primary\" href=\"login.html\">Logout</a>\r\n"
				+ "                </div>\r\n"
				+ "            </div>\r\n"
				+ "        </div>\r\n"
				+ "    </div>\r\n"
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
				+ "\r\n"
				+ "</body>\r\n"
				+ "\r\n"
				+ "</html>";
		
		response.getWriter().write(html);
		
	}
}
