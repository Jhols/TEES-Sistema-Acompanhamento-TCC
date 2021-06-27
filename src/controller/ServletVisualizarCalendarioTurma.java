package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CalendarioDAO;
import dao.EnvioEntregaDAO;
import dao.LoginDAO;
import dao.TurmaDAO;
import enums.Perfil;
import model.Aluno;
import model.Entrega;
import model.EnvioEntrega;
import model.Pessoa;
import model.Turma;
import util.AnexoDeArquivo;

@WebServlet(name = "VisualizarCalendarioTurma", urlPatterns = {"/calendarioTurmaAluno"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class ServletVisualizarCalendarioTurma extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pessoa pessoa = (Pessoa) request.getSession().getAttribute("pessoa");
		if (pessoa == null || pessoa.getPerfil() != Perfil.ALUNO)
		{
			// Se não estiver logado, ou se não for aluno
			response.sendRedirect("login.html");
			return;
		}
		
		String opcao = request.getParameter("opcao");
		if (opcao != null) {
			switch (opcao) {
			case "del":
				int idEnvioEntrega = Integer.parseInt(request.getParameter("envio"));
				EnvioEntregaDAO.removeEnvioEntrega(idEnvioEntrega);
				response.sendRedirect("calendarioTurmaAluno");
				return;
			case "download":
				idEnvioEntrega = Integer.parseInt(request.getParameter("envio"));
				EnvioEntrega envio = EnvioEntregaDAO.buscarEnvioEntregaPorId(idEnvioEntrega);
				AnexoDeArquivo.dowloadArquivo(envio, response);
				return;
			}
		}
		
		Aluno aluno = (Aluno) pessoa;
		int idTurma = TurmaDAO.getInstance().pesquisarTurmaDoAluno(aluno.getIdAluno());
		Turma turma = TurmaDAO.getInstance().pesquisarTurmaPorId(idTurma);
		ArrayList<Entrega> entregas = CalendarioDAO.buscarEntregasPorTurma(idTurma);
		
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
				+ "                            <h5>Calendário de entregas da Turma <b>" + turma.getNome() + " " + turma.getSemestre() + "</b></h5>\r\n"
				+ "                            <h5></h5>\r\n"
				+ "                        </div>\r\n"
				+ "                            <div class=\"table-responsive\">\r\n"
				+ "                                <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\r\n"
				+ "                                    <thead>\r\n"
				+ "                                        <tr>\r\n"
				+ "                                            <th>Titulo</th>\r\n"
				+ "                                            <th>Instrução</th>\r\n"
				+ "                                            <th>Prazo</th>\r\n"
				+ "                                        </tr>\r\n"
				+ "                                    </thead>\r\n";
				for (Entrega entrega : entregas) {
					html += "<tr><td>" + entrega.getTitulo()
					+ "<td>" + entrega.getInstrucao()
					+ "<td>" + entrega.dataFormatada()
					+ "\r\n"
					+ "<tr><td colspan=3>\r\n";
					EnvioEntrega envio = EnvioEntregaDAO.buscarEnvioEntregaPorAluno(entrega.getIdEntrega(), aluno.getIdAluno());
					if (envio == null) {
						html += "<form method=\"POST\" action=\"calendarioTurmaAluno\" enctype=\"multipart/form-data\">\r\n"
								+ "<input class=\"btn btn-primary\" type=\"file\" name=\"arquivo\" required >\r\n"
								+ " <input type=\"hidden\" name=\"idEntrega\" value=\""+ entrega.getIdEntrega() +"\" >\r\n"
								+ "<input type=\"submit\" class=\"btn btn-primary\">"
								+ "</form>\r\n";
					}
					else {
						html += "<div title=\"Clique para fazer download\">";
						html += "<a href=\"calendarioTurmaAluno?opcao=download&envio="+envio.getIdEnvioEntrega() + "\" >" +envio.getFileName() + "</a> entregue em " + envio.dataFormatada() + "\r\n";
						
						if (envio.getDataEnvio().after(entrega.getDataPrazo())) {
							TimeUnit timeUnit = TimeUnit.DAYS;
							long diffInMillies = envio.getDataEnvio().getTime() - entrega.getDataPrazo().getTime() ;
							long dias = timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
							
							html += "(" + dias + " dias de atraso)"; 
						}
						
						html += "  <a class=\"btn btn-primary\" align=\"center\" href=\"calendarioTurmaAluno?opcao=del&envio=" + envio.getIdEnvioEntrega() +"\" role=\"button\">Excluir Arquivo</a>";
						html += "</div>";
					}
					
				}
				html += "</table>\r\n"
				+ "                        <a class=\"btn btn-primary\" align=\"center\" href=\"login.html\" role=\"button\">Login</a>\r\n"
				+ "                        <a class=\"btn btn-primary\" align=\"center\" href=\"alunoDashboard\" role=\"button\">Voltar</a>\r\n"
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pessoa pessoa = (Pessoa) request.getSession().getAttribute("pessoa");
		if (pessoa == null || pessoa.getPerfil() != Perfil.ALUNO)
		{
			pessoa = LoginDAO.pesquisaPessoa("talita@gmail.com","301");
			request.getSession().setAttribute("pessoa", pessoa);
			// Se não estiver logado, ou se não for aluno
			//response.sendRedirect("login.html");
			//return;
		}
		Aluno aluno = (Aluno) pessoa;
		request.setCharacterEncoding("UTF-8");
		
		EnvioEntrega envio = new EnvioEntrega();
		envio.setIdAluno(aluno.getIdAluno());
		envio.setIdEntrega(Integer.parseInt(request.getParameter("idEntrega")));
		envio.setDataEnvio(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));

		if (AnexoDeArquivo.extrairArquivo(envio, request)) {
			EnvioEntregaDAO.addEnvioEntrega(envio);
		}
		else {
			System.out.println("Não foi possivel carregar arquivo");
		}
		
		response.sendRedirect("calendarioTurmaAluno");
		
	}
	

}