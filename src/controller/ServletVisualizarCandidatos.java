package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InscricaoProjetoDAO;
import dao.LoginDAO;
import dao.ProjetoDAO;
import enums.BancoTabela;
import enums.SituacaoProjeto;
import model.InscricaoProjeto;
import model.Professor;
import model.Projeto;

@WebServlet(name = "Candidatos", urlPatterns = {"/candidatos"})
public class ServletVisualizarCandidatos extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		var professor = (Professor) request.getSession().getAttribute("pessoa");
		
		if (professor == null) {
			System.out.println("login automatico");
			professor = (Professor) LoginDAO.pesquisaPessoa("alexandre", "1234");
			request.getSession().setAttribute("pessoa", professor);
		}
		
		var projetos = ProjetoDAO.pesquisarProjetosPorProfessorESituacao(professor.getIdProfessor(), SituacaoProjeto.DISPONIVEL);
		
		
		var linhas = new ArrayList<HashMap<String, String>>();
		
		System.out.println(BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela());
		
		for (Projeto p : projetos) {
			System.out.println(p);
			var inscricoes = InscricaoProjetoDAO.pesquisarInscricoesDeCandidatoParaProjeto(p);
			for (InscricaoProjeto in : inscricoes) {
				System.out.println(in);
				System.out.println(in.getAluno());
				var listIncricoes=InscricaoProjetoDAO.pesquisarInscricoesPorAluno(in.getIdAluno());
				if(listIncricoes.isEmpty()) {
					var linha = new HashMap<String, String>();
					linha.put("titulo", p.getTitulo());
					linha.put("nome candidato", in.getAluno().getNome());
					linha.put("idProjeto", String.valueOf(in.getIdProjeto()));
					linha.put("aluno", String.valueOf(in.getAluno().getId()));
					linhas.add(linha);
				}
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
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
		+ "                    <!-- Page Heading -->\r\n"
		+ "                    <h1 class=\"h3 mb-2 text-gray-800\">Alunos Candidatos</h1>\r\n"
		+ "\r\n"
		+ "                    <!-- DataTales Example -->\r\n"
		+ "                    <div class=\"card shadow mb-4\">\r\n"
		+ "                        \r\n"
		+ "                        <div class=\"card-body\">\r\n"
		+ "                            <div class=\"table-responsive\">\r\n"
		+ "                                <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\r\n"
		+ "                                    <thead>\r\n"
		+ "                                        <tr>\r\n"
		+ "                                            <th>Título do Projeto</th>\r\n"
		+ "                                            <th>Nome do Aluno</th>\r\n"
		+ "                                            <th>Aceitar</th>\r\n"
		+ "                                            <th>Rejeitar</th>\r\n"
		+ "                                        </tr>\r\n"
		+ "                                    </thead>\r\n"
		+ "                                    <tbody>\r\n";
		
		for (var linha : linhas) {
			html += "<tr><td>" + linha.get("titulo") + "<td>" + linha.get("nome candidato");
			html+="<td ><a class=\"btn btn-primary\" href=\"GerarTermo?idProjeto="+ linha.get("idProjeto") + "&aluno="+linha.get("aluno")+"\" role=\"button\">Aceitar</a>";
			html+="<td ><a class=\"btn btn-primary\" href=\"#\" role=\"button\">Rejeitar</a>";
			html += "</tr>";
		}
		
		
		html += 
		 
		 "                                    </tbody>\r\n"
		+ "                                </table>\r\n"
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
