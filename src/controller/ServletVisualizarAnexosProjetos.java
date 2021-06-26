package controller;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.ArquivoDAO;
import dao.ProjetoDAO;
import enums.Perfil;
import model.Aluno;
import model.Arquivo;
import model.Pessoa;
import model.Professor;

@WebServlet(urlPatterns = {"/visualizarAnexosProjetos"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class ServletVisualizarAnexosProjetos extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// Tentar pegar o professor que está logado atualmente
		Pessoa pessoa = (Pessoa) request.getSession().getAttribute("pessoa");
		if (pessoa == null) {
			response.sendRedirect("login.html");
			return;
		}
		if (pessoa.getPerfil() != Perfil.PROFESSOR && pessoa.getPerfil() != Perfil.ALUNO) {
			System.out.println("Não é professor nem aluno");
			response.sendRedirect("login.html");
			return;
		}
		
		int idProjeto = Integer.parseInt(request.getParameter("idProjeto"));
		var arquivos = ArquivoDAO.procurarAnexosPorProjeto(idProjeto);
		var projeto = ProjetoDAO.pesquisarProjetoPorIdProjeto(idProjeto);
		

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
				+ "        <title>SB Admin 2 - Register</title>\r\n"
				+ "        <!-- Custom fonts for this template-->\r\n"
				+ "        <link href=\"resources/bootstrap/vendor/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n"
				+ "        <link href=\"https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i\" rel=\"stylesheet\" />\r\n"
				+ "        <!-- Custom styles for this template-->\r\n"
				+ "        <link href=\"resources/bootstrap/css/sb-admin-2.css\" rel=\"stylesheet\" />\r\n"
				+ "    </head>\r\n"
				+ "    <body class=\"bg-gradient-primary\">\r\n"
				+ "        <form method=\"POST\" action=\"anexarArquivo\" enctype=\"multipart/form-data\">\r\n"
				+ "            <div class=\"container\">\r\n"
				+ "                <div class=\"card o-hidden border-0 shadow-lg my-5\">\r\n"
				+ "                    <div class=\"card-body\">\r\n"
				+ "                        <!-- Nested Row within Card Body -->\r\n"
				+ "                        <div class=\"page-header\">\r\n"
				+ "                            <h1 class=\"text-center font-weight-bold\"></h1>\r\n"
				+ "                            <h5>Arquivos anexos ao projeto <b>" + projeto.getTitulo() +"</b></h5>\r\n"
				+ "                            <h5></h5>\r\n"
				+ "                        </div>\r\n"
				+ "                            <div class=\"table-responsive\">\r\n"
				+ "                                <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\r\n"
				+ "                                    <thead>\r\n"
				+ "                                        <tr>\r\n"
				+ "                                            <th>Arquivo</th>\r\n"
				+ "                                            <th>Download</th>\r\n"
				+ "                                        </tr>\r\n"
				+ "                                    </thead>\r\n";
				
				for (Arquivo arquivo : arquivos) {
					html += "<tr><td>"+arquivo.getFileName() + " <td><a href=\"downloadAnexo?anexo="+arquivo.getIdArquivo()+ "\" >Download</a> \r\n";
				}
				
				html += "</table>"
				
				+ "                            \r\n"
				+ "                            \r\n"
			
				+ "                        <a class=\"btn btn-primary\" align=\"center\" href=\"login.html\" role=\"button\">Login</a>\r\n";
				if (pessoa.getPerfil() == Perfil.PROFESSOR) {
					html += "                        <a class=\"btn btn-primary\" align=\"center\" href=\"projetos_professor.jsp\" role=\"button\">Voltar</a>\r\n";
				}
				else {
					html += "                        <a class=\"btn btn-primary\" align=\"center\" href=\"alunoDashboard\" role=\"button\">Voltar</a>\r\n";
				}
				
				html += "\r\n"
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
				+ "        </form>\r\n"
				+ "        \r\n"
				+ "    </body>\r\n"
				+ "    \r\n"
				+ "</html>\r\n"
				+ "";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(html);
		
	}

}