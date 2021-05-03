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
import enums.SituacaoInscricao;
import enums.SituacaoProjeto;
import model.InscricaoProjeto;
import model.Professor;
import model.Projeto;

@WebServlet(name = "ProjetosAtivos", urlPatterns = {"/projetosAtivos"})
public class ServletVisualizarProjetosAtivos extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		// Tentar pegar o professor que est� logado atualmente
		var professor = (Professor) request.getSession().getAttribute("pessoa");
		
		if (professor == null) {
			// Se n�o houver professor logado, faz login automatico para facilitar os testes
			// Futuramente mudar essa parte para redirecionar para a pagina de login
			System.out.println("login automatico");
			professor = (Professor) LoginDAO.pesquisaPessoa("alexandre", "1234");
			request.getSession().setAttribute("pessoa", professor);
		}
		// verificar se h� alguma a��o a ser executada pelo servlet
		if (verificarAcao(request, response)) {
			// se houve alguma acao a ser executada nesse servlet
			// e a pagina foi redirecionada, para a execu��o da fun��o
			return;
		}
		
		// busca a lista de projetos ativos desse professor
		var projetos = ProjetoDAO.pesquisarProjetosPorProfessorESituacao(professor.getIdProfessor(), SituacaoProjeto.ATIVO);		
		
		// uma lista de linha para preencher a tabela de visualiza��o
		var linhas = new ArrayList<HashMap<String, String>>();
		
		for (Projeto p : projetos) {
			System.out.println(p);
			// busca a inscri��o do tipo 'associado' deste projeto
			var inscricoes = InscricaoProjetoDAO.getInstance().pesquisarInscricoesParaProjeto(p, SituacaoInscricao.ASSOCIADO);
			var inscricao = inscricoes.get(0);
			
			// preencher os dados que ser�o mostrados na tabela
			var linha = new HashMap<String, String>();
			linha.put("titulo", p.getTitulo());
			linha.put("nomeAluno", inscricao.getAluno().getNome());
			linha.put("idInscricao", String.valueOf(inscricao.getId()));
			linhas.add(linha);
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
		+ "    <title>Projetos Associados</title>\r\n"
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
		+ "                    <h1 class=\"h3 mb-2 text-gray-800\">Projetos Associados</h1>\r\n"
		+ "\r\n"
		+ "                    <!-- DataTales Example -->\r\n"
		+ "                    <div class=\"card shadow mb-4\">\r\n"
		+ "                        \r\n"
		+ "                        <div class=\"card-body\">\r\n"
		+ "                            <div class=\"table-responsive\">\r\n"
		+ "                                <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\r\n"
		+ "                                    <thead>\r\n"
		+ "                                        <tr>\r\n"
		+ "                                            <th>T�tulo do Projeto</th>\r\n"
		+ "                                            <th>Nome do Aluno</th>\r\n"
		+ "                                            <th>Desvincular</th>\r\n"
		+ "                                        </tr>\r\n"
		+ "                                    </thead>\r\n"
		+ "                                    <tbody>\r\n";
		
		for (var linha : linhas) {
			html += "<tr><td>" + linha.get("titulo") + "<td>" + linha.get("nomeAluno");
			// o bot�o desvincular passa por parametro o id da inscri��o a ser desvinculada
			html+="<td ><a class=\"btn btn-primary\" href=\"projetosAtivos?acao=desvincular&inscricao="+ linha.get("idInscricao") +"\" role=\"button\">Desvincular</a>";
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
		+ "                        <span aria-hidden=\"true\">�</span>\r\n"
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

	// verifica os parametros da pagina para saber se h� alguma a��o a ser executada pelo servlet
	// retorna true se a a��o redireciona a pagina
	private boolean verificarAcao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if (acao == null) {
			return false;
		}
		
		switch (acao) {
		case "desvincular":
			// na a��o de desvincular deve-se mudar o status da inscri��o especificada para 'desvinculado'
			// e a situa��o do projeto para 'disponivel'
			System.out.println("Desvinculando inscricao ");
			var idInscricao = Integer.parseInt(request.getParameter("inscricao"));
			System.out.println("Id = "+idInscricao);
			InscricaoProjeto inscricao = InscricaoProjetoDAO.getInstance().pesquisarInscricaoPorId(idInscricao);
			System.out.println(""+inscricao);
			InscricaoProjetoDAO.getInstance().atualizar(inscricao, SituacaoInscricao.DESVINCULADO);
			
			Projeto projeto = inscricao.getProjeto();
			projeto.setSituacao(SituacaoProjeto.DISPONIVEL);
			ProjetoDAO.getInstance().atualizar(projeto);
			System.out.println(""+projeto);
			
			response.sendRedirect("projetosAtivos");
			return true;
		}
		
		return false;
	}
}
