package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CalendarioDAO;
import dao.EnvioEntregaDAO;
import dao.InscricaoProjetoDAO;
import dao.LoginDAO;
import dao.ProjetoDAO;
import dao.TurmaDAO;
import enums.Perfil;
import enums.SituacaoProjeto;
import model.Aluno;
import model.Entrega;
import model.EnvioEntrega;
import model.InscricaoProjeto;
import model.Pessoa;
import model.Professor;
import model.Projeto;
import model.Turma;
import util.AnexoDeArquivo;

@WebServlet(urlPatterns = {"/postagemTccOrientador"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class ServletVisualizarPostagemProfessorOrientador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pessoa pessoa = (Pessoa) request.getSession().getAttribute("pessoa");
		if (pessoa == null || pessoa.getPerfil() != Perfil.PROFESSOR)
		{
			
			response.sendRedirect("login.html");
			return;
		}
		Professor professor=(Professor)pessoa;
		ArrayList<Projeto>projetos = ProjetoDAO.pesquisarProjetosPorProfessorESituacao(professor.getIdProfessor(), SituacaoProjeto.ATIVO);
		
		String opcao = request.getParameter("opcao");
		if (opcao != null) {
			switch (opcao) {
			
			case "download":
				int idEnvioEntrega = Integer.parseInt(request.getParameter("envio"));
				EnvioEntrega envio = EnvioEntregaDAO.buscarEnvioEntregaPorId(idEnvioEntrega);
				AnexoDeArquivo.dowloadArquivo(envio, response);
				return;
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String html = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"pt-br\">\r\n"
				+ "    <head>\r\n"
				+ "        <meta charset=\"utf-8\" />\r\n"
				+ "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
				+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />\r\n"
				+ "        <meta name=\"description\" content=\"\" />\r\n"
				+ "        <meta name=\"author\" content=\"\" />\r\n"
				+ "        <title>Calendário da Turma</title>\r\n"
				+ "        <!-- Custom fonts for this template-->\r\n"
				+ "        <link href=\"resources/bootstrap/vendor/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n"
				+ "        <link href=\"https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i\" rel=\"stylesheet\" />\r\n"
				+ "        <!-- Custom styles for this template-->\r\n"
				+ "        <link href=\"resources/bootstrap/css/sb-admin-2.css\" rel=\"stylesheet\" />\r\n"
				+ "    </head>\r\n"
				+ "    <body class=\"bg-gradient-primary\">\r\n"
				+ "            <div class=\"container\">\r\n"
				+ "                <div class=\"card o-hidden border-0 shadow-lg my-5\">\r\n"
				+ "                    <div class=\"card-body\">\r\n"
				+ "                        <!-- Nested Row within Card Body -->\r\n"
				+ "                        <div class=\"page-header\">\r\n"
				+ "                            <h1 class=\"text-center font-weight-bold\"></h1>\r\n"
				//+ "                            <h5>Calendário de entregas da Turma <b>" + turma.getNome() + " " + turma.getSemestre() + "</b></h5>\r\n"
				+ "                            <h5></h5>\r\n"
				+ "                        </div>\r\n";
				
				for(Projeto projeto: projetos){
					InscricaoProjeto inscricao = InscricaoProjetoDAO.getInstance().pesquisarAlunoAssociadoAoProjeto(projeto.getId());
					Aluno aluno = inscricao.getAluno();
					System.out.println("idAluno = " + aluno.getId());
					
					int idTurma = TurmaDAO.getInstance().pesquisarTurmaDoAluno(inscricao.getIdAluno());
					System.out.println("idTurma = " + idTurma);
					Turma turma = TurmaDAO.getInstance().pesquisarTurmaPorId(idTurma);
					ArrayList<Entrega> entregasTurma = CalendarioDAO.buscarEntregasPorTurma(idTurma);
					html += "                            <div class=\"table-responsive\">\r\n"
							+ "Aluno: <b>" + aluno.getNome() + "</b> | Turma: <b>" + turma.getNome() + " " + turma.getSemestre() + "</b>"
							+ "                                <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\r\n"
							+ "                                    <thead>\r\n"
							+ "                                        <tr>\r\n"
							+ "                                            <th>Titulo</th>\r\n"
							+ "                                            <th>Instrução</th>\r\n"
							+ "                                            <th>Prazo</th>\r\n"
							+ "                                        </tr>\r\n"
							+ "                                    </thead>\r\n";
					for (Entrega entrega : entregasTurma) {
						html += "<tr><td>" + entrega.getTitulo()
						+ "<td>" + entrega.getInstrucao()
						+ "<td>" + entrega.dataFormatada()
						+ "\r\n";
						
						EnvioEntrega envio = EnvioEntregaDAO.buscarEnvioEntregaPorAluno(entrega.getIdEntrega(), aluno.getIdAluno());
						if (envio != null) {
							html += "<tr><td colspan=3>\r\n";
							html += "<span title=\"Clique para fazer download\">";
							html += "<a href=\"postagemTccOrientador?opcao=download&envio="+envio.getIdEnvioEntrega() + "\" >" +envio.getFileName() + "</a>";
							html += "</span>";
							html += " entregue em " + envio.dataFormatada() + "\r\n";
						
							if (envio.estaAtrasado()) {
								long dias = envio.calcularDiasDeAtraso();
								html += "(" + dias + " dias de atraso)"; 
							}
						}
						
					}
					html += "</table>";
					
				}
				html += "</table>\r\n"
				+ "                        <a class=\"btn btn-primary\" align=\"center\" href=\"login.html\" role=\"button\">Login</a>\r\n"
				+ "                        <a class=\"btn btn-primary\" align=\"center\" href=\"professorDashboard\" role=\"button\">Voltar</a>\r\n"
				+ "                        \r\n"
				+ "                    </div>\r\n"
				+ "                </div>\r\n"
				+ "            </div>\r\n"
				+ "            <!-- Bootstrap core JavaScript-->\r\n"
				+ "            <script src=\"resources/bootstrap/vendor/jquery/jquery.min.js\"></script>\r\n"
				+ "            <script src=\"resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>\r\n"
				+ "            <!-- Core plugin JavaScript-->\r\n"
				+ "            <script src=\"resources/bootstrap/vendor/jquery-easing/jquery.easing.min.js\"></script>\r\n"
				+ "            <!-- Custom scripts for all pages-->\r\n"
				+ "            <script src=\"resources/bootstrap/js/sb-admin-2.min.js\"></script>\r\n"
				+ "        \r\n"
				+ "        \r\n"
				+ "    </body>\r\n"
				+ "    \r\n"
				+ "</html>\r\n"
				+ "";
		response.getWriter().write(html);
	}
	

	

}